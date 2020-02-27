package com.lightspace.framework.domain.search;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class CourseSearchParam implements Serializable {
    String keyword;
    //first class
    String mt;
    //second class
    String st;


    String grade;


    Float price_min;
    Float price_max;

    String sort;
    String filter;

}
