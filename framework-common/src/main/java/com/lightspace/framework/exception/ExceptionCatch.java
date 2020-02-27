package com.lightspace.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.lightspace.framework.model.response.CommonCode;
import com.lightspace.framework.model.response.ResponseResult;
import com.lightspace.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionCatch {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);

    //define map to record the error
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;
    //define builder object
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    //catch the custom exception
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException customException){
        //记录日志
        LOGGER.error("catch exception:{}",customException.getMessage());
        ResultCode resultCode = customException.getResultCode();
        return new ResponseResult(resultCode);
    }
    //catch normal exception
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception){
        LOGGER.error("catch exception:{}",exception.getMessage());
        if(EXCEPTIONS == null){
            EXCEPTIONS = builder.build();//build exception
        }
        //find the code from exception
        ResultCode resultCode = EXCEPTIONS.get(exception.getClass());
        if(resultCode !=null){
            return new ResponseResult(resultCode);
        }else{
            return new ResponseResult(CommonCode.SERVER_ERROR);
        }


    }

    static {
        //定义异常类型所对应的错误代码
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
    }
}
