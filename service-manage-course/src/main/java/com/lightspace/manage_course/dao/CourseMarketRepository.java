package com.lightspace.manage_course.dao;

import com.lightspace.framework.domain.course.CourseMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseMarketRepository extends JpaRepository<CourseMarket, String> {
}
