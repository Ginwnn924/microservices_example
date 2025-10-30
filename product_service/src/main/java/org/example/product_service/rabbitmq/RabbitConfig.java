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

    // Publishser
    public static final String PRODUCT_EXCHANGE = "product.exchange";


    // Consumer
    public static final String ORDER_EXCHANGE = "order.exchange";
    public static final String ORDER_CREATED_QUEUE = "order.created.queue";
    public static final String ORDER_CREATED_ROUTING_KEY = "order.created";

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public DirectExchange productExchange() {
        return new DirectExchange(PRODUCT_EXCHANGE);
    }





    // Order Created
    @Bean
    public Queue orderQueue() {
        return new Queue(ORDER_CREATED_QUEUE, true); // durable = true
    }

    @Bean
    public Binding bindingOrderCreated() {
        return BindingBuilder.bind(orderQueue())
                .to(new DirectExchange(ORDER_EXCHANGE))
                .with(ORDER_CREATED_ROUTING_KEY);
    }



}
