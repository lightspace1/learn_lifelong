package com.lightspace.manage_course.dao;

import com.github.pagehelper.Page;
import com.lightspace.framework.domain.course.CourseBase;
import com.lightspace.framework.domain.course.ext.CourseInfo;
import com.lightspace.framework.domain.course.request.CourseListRequest;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CourseMapper {
  CourseBase findCourseBaseById(String id);
  Page<CourseBase> findCourseList();
    Page<CourseInfo> findCourseListPage(CourseListRequest courseListRequest);
}
