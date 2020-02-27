package com.lightspace.framework.exception;

import com.lightspace.framework.model.response.ResultCode;

public class ExceptionCast {
    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
