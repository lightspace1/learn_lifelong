package com.lightspace.learning.mq;

import com.alibaba.fastjson.JSON;
import com.lightspace.framework.domain.task.XcTask;
import com.lightspace.framework.model.response.ResponseResult;
import com.lightspace.learning.config.RabbitMQConfig;
import com.lightspace.learning.service.LearningService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ChooseCourseTask {

  @Autowired
  LearningService learningService;

  @Autowired
  RabbitTemplate rabbitTemplate;

  @RabbitListener(queues = RabbitMQConfig.LEARNING_ADDCHOOSECOURSE)
  public void receiveChoosecourseTask(XcTask xcTask){

    String requestBody = xcTask.getRequestBody();
    Map map = JSON.parseObject(requestBody, Map.class);
    String userId = (String) map.get("userId");
    String courseId = (String) map.get("courseId");

    ResponseResult addcourse = learningService.addcourse(userId, courseId, null, null, null, xcTask);
    if(addcourse.isSuccess()){
      rabbitTemplate.convertAndSend(RabbitMQConfig.EX_LEARNING_ADDCHOOSECOURSE,RabbitMQConfig.LEARNING_FINISHADDCHOOSECOURSE_KEY,xcTask);
    }
  }
}
