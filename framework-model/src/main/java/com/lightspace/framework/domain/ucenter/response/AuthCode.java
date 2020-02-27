package com.lightspace.framework.domain.ucenter.response;

import com.google.common.collect.ImmutableMap;
import com.lightspace.framework.model.response.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

@ToString
public enum AuthCode implements ResultCode {
  AUTH_USERNAME_NONE(false,23001,
          "Please input Username!"),
  AUTH_PASSWORD_NONE(false,23002,
          "Please enter the password!"),
  AUTH_VERIFYCODE_NONE(false,23003,
          "Please enter verification code!"),
  AUTH_ACCOUNT_NOTEXISTS(false,23004,
          "Account does not exist!"),
  AUTH_CREDENTIAL_ERROR(false,23005,
          "Incorrect username or password!"),
  AUTH_LOGIN_ERROR(false,23006,
          "An exception occurs during the login process, please try again!"),
  AUTH_LOGIN_APPLYTOKEN_FAIL(false,23007,
          "Application for token failed!"),
  AUTH_LOGIN_TOKEN_SAVEFAIL(false,23008,
          "Failed to store token!");

  //操作代码
  @ApiModelProperty(value =
          "Whether the operation was successful", example = "true", required = true)
  boolean success;

  //操作代码
  @ApiModelProperty(value =
          "Operation code", example = "22001", required = true)
  int code;
  //提示信息
  @ApiModelProperty(value =
          "Operation tips", example = "The operation is too frequent!", required = true)
  String message;
  private AuthCode(boolean success, int code, String message){
    this.success = success;
    this.code = code;
    this.message = message;
  }
  private static final ImmutableMap<Integer, AuthCode> CACHE;

  static {
    final ImmutableMap.Builder<Integer, AuthCode> builder = ImmutableMap.builder();
    for (AuthCode commonCode : values()) {
      builder.put(commonCode.code(), commonCode);
    }
    CACHE = builder.build();
  }

  @Override
  public boolean success() {
    return success;
  }

  @Override
  public int code() {
    return code;
  }

  @Override
  public String message() {
    return message;
  }
}
