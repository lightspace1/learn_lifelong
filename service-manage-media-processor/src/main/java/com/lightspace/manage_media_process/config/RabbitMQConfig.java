package com.lightspace.manage_media_process.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  public static final String EX_MEDIA_PROCESSTASK = "ex_media_processor";
  @Value("${lightsapce.mq.queue-media-video-processor}")
  public  String queue_media_video_processtask;
  @Value("${lightspace.mq.routingkey-media-video}")
  public  String routingkey_media_video;
  public static final int DEFAULT_CONCURRENT = 10;

  @Bean("customContainerFactory")
  public SimpleRabbitListenerContainerFactory containerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConcurrentConsumers(DEFAULT_CONCURRENT);
    factory.setMaxConcurrentConsumers(DEFAULT_CONCURRENT);
    configurer.configure(factory, connectionFactory);
    return factory;
  }

  @Bean(EX_MEDIA_PROCESSTASK)
  public Exchange EX_MEDIA_VIDEOTASK() {
    return ExchangeBuilder.directExchange(EX_MEDIA_PROCESSTASK).durable(true).build();
  }

  @Bean("queue_media_video_processtask")
  public Queue QUEUE_PROCESSTASK() {
    Queue queue = new Queue(queue_media_video_processtask,true,false,true);
    return queue;
  }

  @Bean
  public Binding binding_queue_media_processtask(@Qualifier("queue_media_video_processtask") Queue queue, @Qualifier(EX_MEDIA_PROCESSTASK) Exchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(routingkey_media_video).noargs();
  }
}
