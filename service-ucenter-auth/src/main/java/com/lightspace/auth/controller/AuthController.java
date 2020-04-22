package com.lightspace.auth.controller;

import com.lightspace.api.auth.AuthControllerApi;
import com.lightspace.auth.service.AuthService;
import com.lightspace.framework.domain.ucenter.ext.AuthToken;
import com.lightspace.framework.domain.ucenter.request.LoginRequest;
import com.lightspace.framework.domain.ucenter.response.AuthCode;
import com.lightspace.framework.domain.ucenter.response.JwtResult;
import com.lightspace.framework.domain.ucenter.response.LoginResult;
import com.lightspace.framework.exception.ExceptionCast;
import com.lightspace.framework.model.response.CommonCode;
import com.lightspace.framework.model.response.ResponseResult;
import com.lightspace.framework.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/")
public class AuthController implements AuthControllerApi {

  @Value("${auth.clientId}")
  String clientId;
  @Value("${auth.clientSecret}")
  String clientSecret;
  @Value("${auth.cookieDomain}")
  String cookieDomain;
  @Value("${auth.cookieMaxAge}")
  int cookieMaxAge;

  @Autowired
  AuthService authService;

  @Override
  @PostMapping("/userlogin")
  public LoginResult login(LoginRequest loginRequest) {
    if(loginRequest == null || StringUtils.isEmpty(loginRequest.getUsername())){
      ExceptionCast.cast(AuthCode.AUTH_USERNAME_NONE);
    }
    if(loginRequest == null || StringUtils.isEmpty(loginRequest.getPassword())){
      ExceptionCast.cast(AuthCode.AUTH_PASSWORD_NONE);
    }
    String username = loginRequest.getUsername();
    String password = loginRequest.getPassword();
    AuthToken authToken =  authService.login(username,password,clientId,clientSecret);
    String access_token = authToken.getAccess_token();
    this.saveCookie(access_token);

    return new LoginResult(CommonCode.SUCCESS,access_token);
  }

  private void saveCookie(String token){

    HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    CookieUtil.addCookie(response,cookieDomain,"/","uid",token,cookieMaxAge,false);

  }
  private void clearCookie(String token){

    HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    CookieUtil.addCookie(response,cookieDomain,"/","uid",token,0,false);

  }


  @Override
  @PostMapping("/userlogout")
  public ResponseResult logout() {
    String uid = getTokenFormCookie();
    boolean result = authService.delToken(uid);
    this.clearCookie(uid);
    return new ResponseResult(CommonCode.SUCCESS);
  }

  @Override
  @GetMapping("/userjwt")
  public JwtResult userjwt() {
    String uid = getTokenFormCookie();
    if(uid == null){
      return new JwtResult(CommonCode.FAIL,null);
    }

    AuthToken userToken = authService.getUserToken(uid);
    if(userToken!=null){
      String jwt_token = userToken.getJwt_token();
      return new JwtResult(CommonCode.SUCCESS,jwt_token);
    }
    return null;
  }

  private String getTokenFormCookie(){
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    Map<String, String> map = CookieUtil.readCookie(request, "uid");
    if(map!=null && map.get("uid")!=null){
      String uid = map.get("uid");
      return uid;
    }
    return null;
  }
}
