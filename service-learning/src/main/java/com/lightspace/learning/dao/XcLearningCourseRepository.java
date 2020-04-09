package com.lightspace.learning.dao;

import com.lightspace.framework.domain.learning.XcLearningCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface XcLearningCourseRepository extends JpaRepository<XcLearningCourse,String> {
  XcLearningCourse findByUserIdAndCourseId(String userId,String courseId);
}

