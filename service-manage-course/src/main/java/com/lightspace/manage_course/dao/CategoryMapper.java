package com.lightspace.manage_course.dao;

import com.lightspace.framework.domain.course.ext.CategoryNode;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CategoryMapper {
    public CategoryNode selectList();
}
