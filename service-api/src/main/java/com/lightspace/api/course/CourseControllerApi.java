package com.lightspace.api.course;

import com.lightspace.framework.domain.course.*;
import com.lightspace.framework.domain.course.ext.CourseInfo;
import com.lightspace.framework.domain.course.ext.CourseView;
import com.lightspace.framework.domain.course.ext.TeachplanNode;
import com.lightspace.framework.domain.course.request.CourseListRequest;
import com.lightspace.framework.domain.course.response.AddCourseResult;
import com.lightspace.framework.domain.course.response.CoursePublishResult;
import com.lightspace.framework.model.response.QueryResponseResult;
import com.lightspace.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value=
        "Course Management Interface",description = "course management interface, to add, delete, modify, and check courses")
public interface CourseControllerApi {
    @ApiOperation("query course plan")
    public TeachplanNode findTeachplanList(String courseId);

    @ApiOperation("add course plan")
    public ResponseResult addTeachplan(Teachplan teachplan);
    @ApiOperation("add course picture")
    public ResponseResult addCoursePic(String courseId,String pic);

    @ApiOperation("query course picture")
    public CoursePic findCoursePic(String courseId);

    @ApiOperation("Query the list of my course")
    public QueryResponseResult<CourseInfo> findCourseList(int page, int size, CourseListRequest courseListRequest);

    @ApiOperation("delete course picture")
    public ResponseResult deleteCoursePic(String courseId);

    @ApiOperation("Add course  basis information")
    public AddCourseResult addCourseBase(CourseBase courseBase);

    @ApiOperation("get course  basis information")
    public CourseBase getCourseBaseById(String courseId) throws RuntimeException;
    @ApiOperation("update course  basis information")
    public ResponseResult updateCourseBase(String id,CourseBase courseBase);
    @ApiOperation("get course marketing information")
    public CourseMarket getCourseMarketById(String courseId);
    @ApiOperation("update course marketing information")
    public ResponseResult updateCourseMarket(String id,CourseMarket courseMarket);
    @ApiOperation("get course view")
    public CourseView courseview(String id);
    @ApiOperation("course preview")
    public CoursePublishResult preview(String id);
    @ApiOperation("course publish")
    public CoursePublishResult publish(String id);
  @ApiOperation("Save the lesson plan and media assets file")
  public ResponseResult savemedia(TeachplanMedia teachplanMedia);


}

