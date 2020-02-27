package com.lightspace.framework.exception;

import com.lightspace.framework.model.response.ResultCode;

public class CustomException extends RuntimeException {

    //error code
    ResultCode resultCode;

    public CustomException(ResultCode resultCode){
        this.resultCode = resultCode;
    }
    public ResultCode getResultCode(){
        return resultCode;
    }


}
