package com.lightspace.api.search;

import com.lightspace.framework.domain.course.CoursePub;
import com.lightspace.framework.domain.course.TeachplanMediaPub;
import com.lightspace.framework.domain.search.CourseSearchParam;
import com.lightspace.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Map;

@Api(value =
        "Course search",description =
        "Course search",tags = {
        "Course search"})
public interface EsCourseControllerApi {
    //搜索课程信息
    @ApiOperation(
            "Course Comprehensive Search")
    public QueryResponseResult<CoursePub> list(int page, int size, CourseSearchParam courseSearchParam);
  @ApiOperation(
          "Get course information based on course id")
  public Map<String,CoursePub> getall(String id);

  @ApiOperation(
          "Get course media information according to course plan id")
  public TeachplanMediaPub getmedia(String id);
}

