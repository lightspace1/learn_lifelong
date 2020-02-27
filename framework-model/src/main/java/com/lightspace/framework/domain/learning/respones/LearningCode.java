package com.lightspace.framework.domain.learning.respones;

import com.lightspace.framework.model.response.ResultCode;
import lombok.ToString;

@ToString
public enum LearningCode implements ResultCode {
  LEARNING_GETMEDIA_ERROR(false,23001,"\n" +
          "Failed to obtain the learning address!");
  boolean success;
  int code;
  String message;
  private LearningCode(boolean success, int code, String message){
    this.success = success;
    this.code = code;
    this.message = message;
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
