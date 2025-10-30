package org.example.product_service.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.common.rabbitmq.constant.OrderConstant;
import org.example.common.rabbitmq.constant.ProductConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {


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
    public DirectExchange productExchange() {
        return new DirectExchange(ProductConstant.PRODUCT_EXCHANGE);
    }





    // Order Created
    @Bean
    public Queue orderCreatedQueue() {
        return new Queue(OrderConstant.ORDER_CREATED_QUEUE, true); // durable = true
    }

    @Bean
    public Binding bindingOrderCreated() {
        return BindingBuilder.bind(orderCreatedQueue())
                .to(new DirectExchange(OrderConstant.ORDER_EXCHANGE))
                .with(OrderConstant.ORDER_CREATED_ROUTING_KEY);
    }



}
