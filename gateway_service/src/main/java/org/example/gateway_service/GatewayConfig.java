package org.example.gateway_service;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("product-service", r -> r.path("/products/**")
                        .uri("http://localhost:8081/product-service"))

                .route("order-service", r -> r.path("/orders/**")
                        .uri("http://localhost:8082/order-service"))
                .build();
    }
}
