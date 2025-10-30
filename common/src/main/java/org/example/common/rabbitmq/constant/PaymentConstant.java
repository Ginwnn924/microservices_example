package org.example.common.rabbitmq.constant;

public class PaymentConstant {
    public static final String PAYMENT_EXCHANGE = "payment.exchange";

    public static final String PAYMENT_REQUESTED_QUEUE = "payment.requested.queue";
    public static final String PAYMENT_REQUESTED_ROUTING_KEY = "payment.requested.routing.key";

    public static final String PAYMENT_PROCESSED_QUEUE = "payment.processed.queue";
    public static final String PAYMENT_PROCESSED_ROUTING_KEY = "payment.processed.routing.key";

    public static final String PAYMENT_FAILED_QUEUE = "payment.failed.queue";
    public static final String PAYMENT_FAILED_ROUTING_KEY = "payment.failed.routing.key";



}
