package com.lightspace.framework.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Oauth2Util {

  public static Map<String,String> getJwtClaimsFromHeader(HttpServletRequest request) {
    if (request == null) {
      return null;
    }
    String authorization = request.getHeader("Authorization");
    if (StringUtils.isEmpty(authorization) || authorization.indexOf("Bearer") < 0) {
      return null;
    }
    String token = authorization.substring(7);
    Map<String,String> map = null;
    try {
      Jwt decode = JwtHelper.decode(token);
      String claims = decode.getClaims();
      map = JSON.parseObject(claims, Map.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return map;
  }
}
