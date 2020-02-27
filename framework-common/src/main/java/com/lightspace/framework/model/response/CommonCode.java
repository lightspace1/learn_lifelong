package com.lightspace.framework.model.response;

import lombok.ToString;

@ToString
public enum CommonCode implements ResultCode{
    INVALID_PARAM(false,10003,"Illegal Argument！"),
    SUCCESS(true,10000,"Operation Success！"),
    FAIL(false,11111,"Operation Fail！"),
    UNAUTHENTICATED(false,10001,"Login Required！"),
    UNAUTHORISE(false,10002,"Permission Denial！"),
    SERVER_ERROR(false,99999,"Sorry Please try again！");
    boolean success;

    int code;

    String message;
    private CommonCode(boolean success,int code, String message){
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