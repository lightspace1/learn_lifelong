package com.lightspace.manage_course.dao;

import com.lightspace.framework.domain.course.TeachplanMediaPub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeachplanMediaPubRepository extends JpaRepository<TeachplanMediaPub,String> {
  long deleteByCourseId(String courseId);
}

