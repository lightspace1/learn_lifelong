package com.lightspace.search.controller;

import com.lightspace.api.search.EsCourseControllerApi;
import com.lightspace.framework.domain.course.CoursePub;
import com.lightspace.framework.domain.course.TeachplanMediaPub;
import com.lightspace.framework.domain.search.CourseSearchParam;
import com.lightspace.framework.model.response.QueryResponseResult;
import com.lightspace.framework.model.response.QueryResult;
import com.lightspace.search.service.EsCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search/course")
public class EsCourseController implements EsCourseControllerApi {
    @Autowired
    EsCourseService esCourseService;

    @Override
    @GetMapping(value="/list/{page}/{size}")
    public QueryResponseResult<CoursePub> list(@PathVariable("page") int page, @PathVariable("size") int size, CourseSearchParam courseSearchParam) {
        return esCourseService.list(page,size,courseSearchParam);
    }

  @Override
  @GetMapping("/getall/{id}")
  public Map<String, CoursePub> getall(@PathVariable("id") String id) {
    return esCourseService.getall(id);
  }
  @Override
  @GetMapping(value="/getmedia/{teachplanId}")
  public TeachplanMediaPub getmedia(@PathVariable("teachplanId") String teachplanId) {
    String[] teachplanIds = new String[]{teachplanId};
    QueryResponseResult<TeachplanMediaPub> queryResponseResult = esCourseService.getmedia(teachplanIds);
    QueryResult<TeachplanMediaPub> queryResult = queryResponseResult.getQueryResult();
    if(queryResult != null){
      List<TeachplanMediaPub> list = queryResult.getList();
      if(list !=null && list.size()>0){
        return list.get(0);
      }
    }
    return new TeachplanMediaPub();
  }
}
