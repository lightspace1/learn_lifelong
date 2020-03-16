package com.lightspace.order.service;

import com.lightspace.framework.domain.task.XcTask;
import com.lightspace.framework.domain.task.XcTaskHis;
import com.lightspace.order.dao.XcTaskHisRepository;
import com.lightspace.order.dao.XcTaskRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

  @Autowired
  XcTaskRepository xcTaskRepository;

  @Autowired
  RabbitTemplate rabbitTemplate;

  @Autowired
  XcTaskHisRepository xcTaskHisRepository;

  public List<XcTask> findXcTaskList(Date updateTime, int size){
    Pageable pageable = new PageRequest(0,size);
    Page<XcTask> all = xcTaskRepository.findByUpdateTimeBefore(pageable, updateTime);
    List<XcTask> list = all.getContent();
    return list;
  }

  public void publish(XcTask xcTask,String ex,String routingKey){
    Optional<XcTask> optional = xcTaskRepository.findById(xcTask.getId());
    if(optional.isPresent()){
      rabbitTemplate.convertAndSend(ex,routingKey,xcTask);
      XcTask one = optional.get();
      one.setUpdateTime(new Date());
      xcTaskRepository.save(one);
    }



  }

  @Transactional
  public int getTask(String id,int version){
    int count = xcTaskRepository.updateTaskVersion(id, version);
    return count;
  }

  @Transactional
  public void finishTask(String taskId){
    Optional<XcTask> optionalXcTask = xcTaskRepository.findById(taskId);
    if(optionalXcTask.isPresent()){
      XcTask xcTask = optionalXcTask.get();
      XcTaskHis xcTaskHis = new XcTaskHis();
      BeanUtils.copyProperties(xcTask,xcTaskHis);
      xcTaskHisRepository.save(xcTaskHis);
      xcTaskRepository.delete(xcTask);
    }
  }
}
