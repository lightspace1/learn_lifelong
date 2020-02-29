package com.lightspace.api.auth;

import com.lightspace.framework.domain.ucenter.request.LoginRequest;
import com.lightspace.framework.domain.ucenter.response.JwtResult;
import com.lightspace.framework.domain.ucenter.response.LoginResult;
import com.lightspace.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "user authentication",description = "user authentication interface")
public interface AuthControllerApi {
  @ApiOperation("login")
  public LoginResult login(LoginRequest loginRequest);

  @ApiOperation("logout")
  public ResponseResult logout();

  @ApiOperation("query user jwt token")
  public JwtResult userjwt();
}


