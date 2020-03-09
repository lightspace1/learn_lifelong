package com.lightspace.manage_course.controller;

import com.lightspace.api.course.CourseControllerApi;
import com.lightspace.framework.domain.course.*;
import com.lightspace.framework.domain.course.ext.CourseInfo;
import com.lightspace.framework.domain.course.ext.CourseView;
import com.lightspace.framework.domain.course.ext.TeachplanNode;
import com.lightspace.framework.domain.course.request.CourseListRequest;
import com.lightspace.framework.domain.course.response.AddCourseResult;
import com.lightspace.framework.domain.course.response.CoursePublishResult;
import com.lightspace.framework.model.response.CommonCode;
import com.lightspace.framework.model.response.QueryResponseResult;
import com.lightspace.framework.model.response.ResponseResult;
import com.lightspace.framework.utils.XcOauth2Util;
import com.lightspace.framework.web.BaseController;
import com.lightspace.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController extends BaseController implements CourseControllerApi {

    @Autowired
    CourseService courseService;

    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {
        return courseService.findTeachplanList(courseId);
    }

    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody  Teachplan teachplan) {
        return courseService.addTeachplan(teachplan);
    }

    @PreAuthorize("hasAuthority('course_pic_list')")
    @Override
    @PostMapping("/coursepic/add")
    public ResponseResult addCoursePic(@RequestParam("courseId") String courseId, @RequestParam("pic")String pic) {
        return courseService.addCoursePic(courseId,pic);
    }




    @Override
    @PostMapping("/coursebase/add")
    public AddCourseResult addCourseBase(@RequestBody CourseBase courseBase) {
        return courseService.addCourseBase(courseBase);
    }
    @Override
    @GetMapping("/coursepic/list/{courseId}")
    public CoursePic findCoursePic(@PathVariable("courseId") String courseId) {
        return courseService.findCoursePic(courseId);
    }
    @Override
    @DeleteMapping("/coursepic/delete")
    public ResponseResult deleteCoursePic(@RequestParam("courseId") String courseId) {
        return courseService.deleteCoursePic(courseId);
    }
    @Override
    @GetMapping("/coursebase/get/{courseId}")
    public CourseBase getCourseBaseById(@PathVariable("courseId") String courseId) throws
            RuntimeException {
        return courseService.getCoursebaseById(courseId);
    } @
            Override
    @PutMapping("/coursebase/update/{id}")
    public ResponseResult updateCourseBase(@PathVariable("id") String id, @RequestBody CourseBase
            courseBase) {
        return courseService.updateCoursebase(id,courseBase);
    }

    @Override
    @PostMapping("/coursemarket/update/{id}")
    public ResponseResult updateCourseMarket(@PathVariable("id") String id, @RequestBody CourseMarket
            courseMarket) {
        CourseMarket courseMarket_u = courseService.updateCourseMarket(id, courseMarket);
        if(courseMarket_u!=null){
            return new ResponseResult(CommonCode.SUCCESS);
        }else{
            return new ResponseResult(CommonCode.FAIL);
        }
    }

    @Override
    @GetMapping("/coursemarket/get/{courseId}")
    public CourseMarket getCourseMarketById(@PathVariable("courseId") String courseId) {
        return courseService.getCourseMarketById(courseId);
    }

    @Override
    @GetMapping("/courseview/{id}")
    public CourseView courseview(@PathVariable("id") String id) {
        return courseService.getCoruseView(id);
    }

    @Override
    @PostMapping("/preview/{id}")
    public CoursePublishResult preview(@PathVariable("id") String id) {
        return courseService.preview(id);

    }

    @Override
    @PostMapping("/publish/{id}")
    public CoursePublishResult publish(@PathVariable("id")String id) {
        return courseService.publish(id);
    }

  @Override
  @PostMapping("/savemedia")
  public ResponseResult savemedia(@RequestBody TeachplanMedia teachplanMedia) {
    return courseService.savemedia(teachplanMedia);
  }

  @Override
  @GetMapping("/coursebase/list/{page}/{size}")
  public QueryResponseResult<CourseInfo> findCourseList(@PathVariable("page") int page,
                                                        @PathVariable("size") int size,
                                                        CourseListRequest courseListRequest) {

    XcOauth2Util xcOauth2Util = new XcOauth2Util();
    XcOauth2Util.UserJwt userJwt = xcOauth2Util.getUserJwtFromHeader(request);
    String company_id = userJwt.getCompanyId();
    QueryResponseResult<CourseInfo> queryResponseResult = courseService.findCourseList(company_id,page,size,courseListRequest);
    return queryResponseResult;
  }


}
