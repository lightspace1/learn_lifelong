package com.lightspace.auth.client;

import com.lightspace.framework.client.ServiceList;
import com.lightspace.framework.domain.ucenter.ext.XcUserExt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = ServiceList.SERVICE_UCENTER)
public interface UserClient {
  @GetMapping("/ucenter/getuserext")
  public XcUserExt getUserext(@RequestParam("username") String username);
}

