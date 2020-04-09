package com.lightspace.learning.service;

import com.lightspace.framework.domain.course.TeachplanMediaPub;
import com.lightspace.framework.domain.learning.XcLearningCourse;
import com.lightspace.framework.domain.learning.respones.GetMediaResult;
import com.lightspace.framework.domain.learning.respones.LearningCode;
import com.lightspace.framework.domain.task.XcTask;
import com.lightspace.framework.domain.task.XcTaskHis;
import com.lightspace.framework.exception.ExceptionCast;
import com.lightspace.framework.model.response.CommonCode;
import com.lightspace.framework.model.response.ResponseResult;
import com.lightspace.learning.client.CourseSearchClient;
import com.lightspace.learning.dao.XcLearningCourseRepository;
import com.lightspace.learning.dao.XcTaskHisRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class LearningService {

  @Autowired
  XcLearningCourseRepository xcLearningCourseRepository;

  @Autowired
  XcTaskHisRepository xcTaskHisRepository;

  @Autowired
  CourseSearchClient courseSearchClient;

  public GetMediaResult getmedia(String courseId, String teachplanId) {

    TeachplanMediaPub teachplanMediaPub = courseSearchClient.getmedia(teachplanId);
    if(teachplanMediaPub == null || StringUtils.isEmpty(teachplanMediaPub.getMediaUrl())){

      ExceptionCast.cast(LearningCode.LEARNING_GETMEDIA_ERROR);
    }
    return new GetMediaResult(CommonCode.SUCCESS,teachplanMediaPub.getMediaUrl());
  }

  @Transactional
  public ResponseResult addcourse(String userId, String courseId, String valid, Date startTime, Date endTime, XcTask xcTask){
    if (StringUtils.isEmpty(courseId)) {
      ExceptionCast.cast(LearningCode.LEARNING_GETMEDIA_ERROR);
    }

    XcLearningCourse xcLearningCourse = xcLearningCourseRepository.findByUserIdAndCourseId(userId, courseId);

    if(xcLearningCourse!=null){

      xcLearningCourse.setStartTime(startTime);
      xcLearningCourse.setEndTime(endTime);
      xcLearningCourse.setStatus("501001");
      xcLearningCourseRepository.save(xcLearningCourse);
    }else{

      xcLearningCourse = new XcLearningCourse();
      xcLearningCourse.setUserId(userId);
      xcLearningCourse.setCourseId(courseId);
      xcLearningCourse.setStartTime(startTime);
      xcLearningCourse.setEndTime(endTime);
      xcLearningCourse.setStatus("501001");
      xcLearningCourseRepository.save(xcLearningCourse);

    }


    Optional<XcTaskHis> optional = xcTaskHisRepository.findById(xcTask.getId());
    if(!optional.isPresent()){

      XcTaskHis xcTaskHis = new XcTaskHis();
      BeanUtils.copyProperties(xcTask,xcTaskHis);
      xcTaskHisRepository.save(xcTaskHis);
    }
    return new ResponseResult(CommonCode.SUCCESS);
  }
}
