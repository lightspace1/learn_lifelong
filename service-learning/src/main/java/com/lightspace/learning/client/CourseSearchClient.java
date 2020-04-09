package com.lightspace.learning.client;

import com.lightspace.framework.client.ServiceList;
import com.lightspace.framework.domain.course.TeachplanMediaPub;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value= ServiceList.SERVICE_SEARCH)
public interface CourseSearchClient {

  @GetMapping("/search/course/getmedia/{teachplanId}")
  public TeachplanMediaPub getmedia(@PathVariable("teachplanId") String teachplanId);
}
