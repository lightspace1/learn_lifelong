package com.lightspace.framework.domain.course.request;

import com.lightspace.framework.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CourseListRequest extends RequestData {
    private String companyId;
}
