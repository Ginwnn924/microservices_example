package org.example.gateway_service;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {

    // Key Resolver - rate limit theo IP
    @Bean
    @Primary
    public KeyResolver ipKeyResolver() {
        return exchange -> {
            String ip = exchange.getRequest()
                    .getRemoteAddress()
                    .getAddress()
                    .getHostAddress();

            System.out.println("🔑 Rate Limit Key: " + ip);

            return Mono.just(ip);
        };
    }

    // Rate Limiter Bean - Spring sẽ tự inject dependencies
    @Bean
    @Primary
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(10, 20); // default: 10 req/s, burst 20
    }

    // Rate Limiter cho Product Service: 5 req/s
    @Bean
    public RedisRateLimiter productRateLimiter() {
        return new RedisRateLimiter(5, 10);
    }



    // Routes
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("product-service", r -> r
                        .path("/products/**")
                        .filters(f -> f.requestRateLimiter(c ->
                                c.setRateLimiter(productRateLimiter())
                                .setKeyResolver(ipKeyResolver())
                        ))
                        .uri("lb://product-service")
                )

                .route("order-service", r -> r
                        .path("/orders/**")
                        .uri("lb://order-service")
                )

                .route("payment-service", r -> r
                        .path("/payments/**")
                        .uri("lb://payment-service")
                )

                .build();
    }
}
