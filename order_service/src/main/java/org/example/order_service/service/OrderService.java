package org.example.order_service.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.order_service.PaymentClient;
import org.example.order_service.ProductClient;
import org.example.order_service.entity.OrderEntity;
import org.example.order_service.entity.OrderItemEntity;
import org.example.order_service.entity.OrderStatus;
import org.example.order_service.rabbitmq.OrderProducer;
import org.example.order_service.rabbitmq.event.OrderCreatedEvent;
import org.example.order_service.rabbitmq.event.OrderItemEvent;
import org.example.order_service.repository.OrderRepository;
import org.example.order_service.request.OrderRequest;
import org.example.order_service.request.PaymenRequest;
import org.example.order_service.response.OrderResponse;
import org.example.order_service.response.PaymentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;
    private final OrderProducer orderProducer;

    public PaymentResponse createOrder(OrderRequest orderRequest) {
        // Gia su data truyen vao hop le
        OrderEntity orderEntity = OrderEntity.builder()
                .totalPrice(orderRequest.getTotalPrice())
                .status(OrderStatus.CREATED)
                .build();

        List<OrderItemEntity> items = orderRequest.getListItems().stream()
                .map(item -> {
                    OrderItemEntity orderItem = OrderItemEntity.builder()
                            .productId(item.getProductId())
                            .quantity(item.getQuantity())
                            .price(item.getPrice())
                            .order(orderEntity)
                            .build();
                    return orderItem;
                }).toList();

        orderEntity.setListItems(items);
        orderRepository.save(orderEntity);

        // Convert sang messsage va gui di
        OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.builder()
                .orderId(orderEntity.getId())
                .totalPrice(orderEntity.getTotalPrice())
                .listItems(orderEntity.getListItems().stream().map(item -> OrderItemEvent.builder()
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build()).toList())
                .build();

        orderProducer.sendOrderCreated(orderCreatedEvent);

        PaymenRequest paymenRequest = PaymenRequest.builder()
                .bookingId(orderEntity.getId())
                .amount(orderEntity.getTotalPrice())
                .build();


        return PaymentResponse.builder()
                .bookingId(orderEntity.getId())
                .amount(orderEntity.getTotalPrice())
                .status(orderEntity.getStatus().toString())
                .url(paymentClient.createPaymentUrl(paymenRequest))
                .build();
    }

    public OrderResponse getOrderById(int id) {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
//        ProductResponse product = productClient.getProductById(order.getProductId());
        return OrderResponse.builder()
                .id(orderEntity.getId())
//                .totalPrice(String.valueOf(orderEntity.getTotalPrice()))
                .status(orderEntity.getStatus())
//                .product(product)
                .build();
    }



}
