package com.abc.component.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitMQConfig {

    @Bean
    Queue userQueue() {
        return new Queue("userQueue", true);
    }

    @Bean
    DirectExchange userDirectExchange() {
        return new DirectExchange("userExchange");
    }

    @Bean
    Binding bindingDirectExchange() {
        return BindingBuilder.bind(userQueue()).to(userDirectExchange()).with("userDirectRouting");
    }
}