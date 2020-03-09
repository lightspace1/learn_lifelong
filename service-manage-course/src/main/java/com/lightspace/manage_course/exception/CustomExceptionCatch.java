package com.lightspace.manage_course.exception;

import com.lightspace.framework.exception.ExceptionCatch;
import com.lightspace.framework.model.response.CommonCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class CustomExceptionCatch  extends ExceptionCatch {
  static {
    builder.put(AccessDeniedException.class, CommonCode.UNAUTHORISE);
  }
}
