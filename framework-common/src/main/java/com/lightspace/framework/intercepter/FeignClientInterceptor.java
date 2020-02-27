package com.lightspace.framework.intercepter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class FeignClientInterceptor implements RequestInterceptor {


  @Override
  public void apply(RequestTemplate requestTemplate) {
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if(requestAttributes!=null){
      HttpServletRequest request = requestAttributes.getRequest();
      //get the jwt token from header
      Enumeration<String> headerNames = request.getHeaderNames();
      if(headerNames!=null){
        while (headerNames.hasMoreElements()){
          String headerName = headerNames.nextElement();
          String headerValue = request.getHeader(headerName);
          requestTemplate.header(headerName,headerValue);

        }
      }
    }



  }
}
