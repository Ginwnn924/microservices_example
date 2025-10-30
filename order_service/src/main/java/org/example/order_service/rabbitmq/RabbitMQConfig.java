package org.example.order_service.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.common.rabbitmq.constant.OrderConstant;
import org.example.common.rabbitmq.constant.PaymentConstant;
import org.example.common.rabbitmq.constant.ProductConstant;
import org.springframework.amqp.core.*;


import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    @Bean
    public MessageConverter messageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();

        // Enable serialize/deserialize empty beans
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        converter.setCreateMessageIds(true);

        return converter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(OrderConstant.ORDER_EXCHANGE);
    }

    @Bean
    public DirectExchange paymentExchange() {
        return new DirectExchange(PaymentConstant.PAYMENT_EXCHANGE);
    }

    // Order failed
    @Bean
    public Queue orderFailedQueue() {
        return new Queue(OrderConstant.ORDER_FAILED_QUEUE, true); // durable = true
    }

    @Bean
    public Binding bindingOrderFailed() {
        return BindingBuilder.bind(orderFailedQueue())
                .to(new DirectExchange(OrderConstant.ORDER_EXCHANGE))
                .with(OrderConstant.ORDER_FAILED_ROUTING_KEY);
    }



    // Reserved
    @Bean
    public Queue reservedQueue() {
        return new Queue(ProductConstant.PRODUCT_RESERVED_QUEUE, true); // durable = true
    }

    @Bean
    public Binding bindingReservedQueue() {
        return BindingBuilder.bind(reservedQueue())
                .to(new DirectExchange(ProductConstant.PRODUCT_EXCHANGE))
                .with(ProductConstant.PRODUCT_RESERVED_ROUTING_KEY);
    }


    // Payment processed
    @Bean
    public Queue paymentProcessedQueue() {
        return new Queue(PaymentConstant.PAYMENT_PROCESSED_QUEUE, true); // durable = true
    }

    @Bean
    public Binding bindingPaymentProcessed() {
        return BindingBuilder.bind(paymentProcessedQueue())
                .to(new DirectExchange(PaymentConstant.PAYMENT_EXCHANGE))
                .with(PaymentConstant.PAYMENT_PROCESSED_ROUTING_KEY);
    }

    // Paymment failed
    @Bean
    public Queue paymentFailedQueue() {
        return new Queue(PaymentConstant.PAYMENT_FAILED_QUEUE, true); // durable = true
    }

    @Bean
    public Binding bindingPaymentFailed() {
        return BindingBuilder.bind(paymentFailedQueue())
                .to(new DirectExchange(PaymentConstant.PAYMENT_EXCHANGE))
                .with(PaymentConstant.PAYMENT_FAILED_ROUTING_KEY);
    }
}
