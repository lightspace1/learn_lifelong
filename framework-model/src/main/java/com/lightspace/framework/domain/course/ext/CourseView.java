package com.lightspace.framework.domain.course.ext;

import com.lightspace.framework.domain.course.CourseBase;
import com.lightspace.framework.domain.course.CourseMarket;
import com.lightspace.framework.domain.course.CoursePic;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class CourseView implements java.io.Serializable {
    private CourseBase courseBase;
    private CoursePic coursePic;
    private CourseMarket courseMarket;
    private TeachplanNode teachplanNode;
}
