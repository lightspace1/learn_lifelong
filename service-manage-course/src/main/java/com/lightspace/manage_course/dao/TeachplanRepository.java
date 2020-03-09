package com.lightspace.manage_course.dao;

import com.lightspace.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeachplanRepository extends JpaRepository<Teachplan,String> {
    List<Teachplan> findByCourseidAndParentid(String courseId, String parentId);
}

