package com.lightspace.learning.controller;

import com.lightspace.api.learning.CourseLearningControllerApi;
import com.lightspace.framework.domain.learning.respones.GetMediaResult;
import com.lightspace.learning.service.LearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/learning/course")
public class CourseLearningController implements CourseLearningControllerApi {

  @Autowired
  LearningService learningService;

  @Override
  @GetMapping("/getmedia/{courseId}/{teachplanId}")
  public GetMediaResult getmedia(@PathVariable("courseId") String courseId,
                                 @PathVariable("teachplanId")String teachplanId) {

    return learningService.getmedia(courseId,teachplanId);
  }
}
