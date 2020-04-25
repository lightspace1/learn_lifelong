package com.lightspace.ucenter.controller;

import com.lightspace.api.ucenter.UcenterControllerApi;
import com.lightspace.framework.domain.ucenter.ext.XcUserExt;
import com.lightspace.ucenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ucenter")
public class UcenterController implements UcenterControllerApi {
  @Autowired
  UserService userService;

  @Override
  @GetMapping("/getuserext")
  public XcUserExt getUserext(@RequestParam("username") String username) {
    return userService.getUserExt(username);
  }
}
