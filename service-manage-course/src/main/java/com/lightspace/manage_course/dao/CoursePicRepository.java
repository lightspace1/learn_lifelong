package com.lightspace.manage_course.dao;

import com.lightspace.framework.domain.course.CoursePic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursePicRepository extends JpaRepository<CoursePic,String> {
    long deleteByCourseid(String courseId);
}