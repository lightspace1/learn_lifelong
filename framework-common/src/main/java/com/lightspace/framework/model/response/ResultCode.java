package com.lightspace.framework.model.response;

public interface ResultCode {

    boolean success();

    int code();

    String message();
}
