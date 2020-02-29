package com.lightspace.api.learning;

import com.lightspace.framework.domain.learning.respones.GetMediaResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Recording course learning management",description =
        "Recording course learning management")
public interface CourseLearningControllerApi {

  @ApiOperation("Get course address")
  public GetMediaResult getmedia(String courseId, String teachplanId);
}

