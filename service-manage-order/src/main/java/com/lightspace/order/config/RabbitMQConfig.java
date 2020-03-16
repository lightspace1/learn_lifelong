package com.lightspace.order.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  public static final String EX_LEARNING_ADDCHOOSECOURSE = "ex_learning_addchoosecourse";
  public static final String LEARNING_FINISHADDCHOOSECOURSE = "learning_finishaddchoosecourse";
  public static final String LEARNING_ADDCHOOSECOURSE = "learning_addchoosecourse";
  public static final String LEARNING_ADDCHOOSECOURSE_KEY = "addchoosecourse";
  public static final String LEARNING_FINISHADDCHOOSECOURSE_KEY = "finishaddchoosecourse";
  
  @Bean(EX_LEARNING_ADDCHOOSECOURSE)
  public Exchange EX_DECLARE() {
    return ExchangeBuilder.directExchange(EX_LEARNING_ADDCHOOSECOURSE).durable(true).build();
  }

  @Bean(LEARNING_FINISHADDCHOOSECOURSE)
  public Queue QUEUE_DECLARE() {
    Queue queue = new Queue(LEARNING_FINISHADDCHOOSECOURSE,true,false,true);
    return queue;
  }
  
  @Bean(LEARNING_ADDCHOOSECOURSE)
  public Queue QUEUE_DECLARE_2() {
    Queue queue = new Queue(LEARNING_ADDCHOOSECOURSE,true,false,true);
    return queue;
  }

  @Bean
  public Binding binding_finishaddchoose_processtask(@Qualifier("learning_finishaddchoosecourse") Queue queue, @Qualifier(EX_LEARNING_ADDCHOOSECOURSE) Exchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(LEARNING_FINISHADDCHOOSECOURSE_KEY).noargs();
  }

  @Bean
  public Binding binding_addchoose_processtask(@Qualifier("learning_addchoosecourse") Queue queue, @Qualifier(EX_LEARNING_ADDCHOOSECOURSE) Exchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(LEARNING_ADDCHOOSECOURSE_KEY).noargs();
  }

}
