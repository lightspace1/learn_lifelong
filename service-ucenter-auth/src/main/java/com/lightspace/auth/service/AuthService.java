package com.lightspace.auth.service;
import com.alibaba.fastjson.JSON;
import com.lightspace.framework.client.ServiceList;
import com.lightspace.framework.domain.ucenter.ext.AuthToken;
import com.lightspace.framework.domain.ucenter.response.AuthCode;
import com.lightspace.framework.exception.ExceptionCast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {

  @Value("${auth.tokenValiditySeconds}")
  int tokenValiditySeconds;
  @Autowired
  LoadBalancerClient loadBalancerClient;

  @Autowired
  StringRedisTemplate stringRedisTemplate;

  @Autowired
  RestTemplate restTemplate;
  public AuthToken login(String username, String password, String clientId, String clientSecret) {

    AuthToken authToken = this.applyToken(username, password, clientId, clientSecret);
    if(authToken == null){
      ExceptionCast.cast(AuthCode.AUTH_LOGIN_APPLYTOKEN_FAIL);
    }
    String access_token = authToken.getAccess_token();
    String jsonString = JSON.toJSONString(authToken);
    boolean result = this.saveToken(access_token, jsonString, tokenValiditySeconds);
    if (!result) {
      ExceptionCast.cast(AuthCode.AUTH_LOGIN_TOKEN_SAVEFAIL);
    }
    return authToken;

  }

  private boolean saveToken(String access_token,String content,long ttl){
    String key = "user_token:" + access_token;
    stringRedisTemplate.boundValueOps(key).set(content,ttl, TimeUnit.SECONDS);
    Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
    return expire>0;
  }

  public boolean delToken(String access_token){
    String key = "user_token:" + access_token;
    stringRedisTemplate.delete(key);
    return true;
  }

  public AuthToken getUserToken(String token){
    String key = "user_token:" + token;

    String value = stringRedisTemplate.opsForValue().get(key);

    try {
      AuthToken authToken = JSON.parseObject(value, AuthToken.class);
      return authToken;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }

  private AuthToken applyToken(String username, String password, String clientId, String clientSecret){

    ServiceInstance serviceInstance = loadBalancerClient.choose(ServiceList.SERVICE_UCENTER_AUTH);

    URI uri = serviceInstance.getUri();

    String authUrl = uri+ "/auth/oauth/token";

    LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();
    String httpBasic = getHttpBasic(clientId, clientSecret);
    header.add("Authorization",httpBasic);


    LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type","password");
    body.add("username",username);
    body.add("password",password);

    HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, header);

    restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
      @Override
      public void handleError(ClientHttpResponse response) throws IOException {
        if(response.getRawStatusCode()!=400 && response.getRawStatusCode()!=401){
          super.handleError(response);
        }
      }
    });

    ResponseEntity<Map> exchange = restTemplate.exchange(authUrl, HttpMethod.POST, httpEntity, Map.class);


    Map bodyMap = exchange.getBody();
    if(bodyMap == null ||
      bodyMap.get("access_token") == null ||
      bodyMap.get("refresh_token") == null ||
      bodyMap.get("jti") == null){


      if(bodyMap!=null && bodyMap.get("error_description")!=null){
        String error_description = (String) bodyMap.get("error_description");
        if(error_description.indexOf("UserDetailsService returned null")>=0){
          ExceptionCast.cast(AuthCode.AUTH_ACCOUNT_NOTEXISTS);
        }else if(error_description.indexOf("坏的凭证")>=0){
          ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
        }
      }


      return null;
    }
    AuthToken authToken = new AuthToken();
    authToken.setAccess_token((String) bodyMap.get("jti"));
    authToken.setRefresh_token((String) bodyMap.get("refresh_token"));
    authToken.setJwt_token((String) bodyMap.get("access_token"));
    return authToken;
  }



  private String getHttpBasic(String clientId,String clientSecret){
    String string = clientId+":"+clientSecret;
    byte[] encode = Base64Utils.encode(string.getBytes());
    return "Basic "+new String(encode);
  }
}
