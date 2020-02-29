package com.lightspace.api.course;

import com.lightspace.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value =
        "Course Classification Management",description =
        "Course Classification Management",tags = {
        "Course Classification Management"})
public interface CategoryControllerApi {
    @ApiOperation("Query classification")
    public CategoryNode findList();
}
