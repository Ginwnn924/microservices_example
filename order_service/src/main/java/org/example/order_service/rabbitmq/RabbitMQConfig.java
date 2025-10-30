package org.example.order_service.rabbitmq;

import org.springframework.amqp.core.*;


import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Publisher
    public static final String ORDER_EXCHANGE = "order.exchange";


    // Consumer
    public static final String ORDER_FAILED_QUEUE = "order.failed.queue";
    public static final String ORDER_FAILED_ROUTING_KEY = "order.failed";

    public static final String ORDER_RESERVED_QUEUE = "order.reserved.queue";
    public static final String ORDER_RESERVED_ROUTING_KEY = "order.reserved";


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE);
    }


    // Order failed
    @Bean
    public Queue orderFailedQueue() {
        return new Queue(ORDER_FAILED_QUEUE, true); // durable = true
    }

    @Bean
    public Binding bindingOrderFailed() {
        return BindingBuilder.bind(orderFailedQueue())
                .to(new DirectExchange(ORDER_EXCHANGE))
                .with(ORDER_FAILED_ROUTING_KEY);
    }



    // Reserved
    @Bean
    public Queue reservedQueue() {
        return new Queue(ORDER_RESERVED_QUEUE, true); // durable = true
    }

    @Bean
    public Binding bindingReservedQueue() {
        return BindingBuilder.bind(reservedQueue())
                .to(new DirectExchange(ORDER_EXCHANGE))
                .with(ORDER_RESERVED_ROUTING_KEY);
    }
}
