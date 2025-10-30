package org.example.payment_service.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.common.rabbitmq.constant.PaymentConstant;
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
    public Queue requestedPaymentQueue() {
        return new Queue(PaymentConstant.PAYMENT_REQUESTED_QUEUE, true); // durable = true
    }

    @Bean
    public Binding bindingRequestedPaymentQueue() {
        return BindingBuilder.bind(requestedPaymentQueue())
                .to(new DirectExchange(PaymentConstant.PAYMENT_EXCHANGE))
                .with(PaymentConstant.PAYMENT_REQUESTED_ROUTING_KEY);
    }
}
