package com.lightspace.manage_course.service;

import com.lightspace.framework.domain.course.ext.CategoryNode;
import com.lightspace.manage_course.dao.CategoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CategoryService {

    @Resource
    CategoryMapper categoryMapper;
    public CategoryNode findList(){
        return categoryMapper.selectList();
    }
}
