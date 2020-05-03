package com.abc.component.rabbitmq;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicRabbitMQConfig {

    @Bean
    public Queue indexQueue(){
        return new Queue("topic.index");
    }

    @Bean
    public Queue htmlQueue(){
        return new Queue("topic.html");
    }
    
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Binding bindingExchange(){
        return BindingBuilder.bind(indexQueue()).to(topicExchange()).with("topic.add");
    }

    @Bean
    public Binding bindingExchange2(){
        return BindingBuilder.bind(htmlQueue()).to(topicExchange()).with("topic.#");
    }
}