package org.example.product_service.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String ORDER_EXCHANGE = "order.exchange";
    public static final String ORDER_FAILED_QUEUE = "order.failed.queue";
    public static final String ORDER_FAILED_ROUTING_KEY = "order.failed";

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE);
    }

    @Bean
    public Queue orderfailedQueue() {
        return new Queue(ORDER_FAILED_QUEUE, true); // durable = true
    }

    @Bean
    public Binding bindingOrderFailed() {
        return BindingBuilder.bind(orderfailedQueue())
                .to(orderExchange())
                .with(ORDER_FAILED_ROUTING_KEY);
    }
}
