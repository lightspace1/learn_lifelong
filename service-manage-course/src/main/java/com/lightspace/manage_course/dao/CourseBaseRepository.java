package com.lightspace.manage_course.dao;

import com.lightspace.framework.domain.course.CourseBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseBaseRepository extends JpaRepository<CourseBase,String> {
}

