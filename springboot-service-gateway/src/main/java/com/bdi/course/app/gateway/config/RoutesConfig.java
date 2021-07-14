package com.bdi.course.app.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(r -> r.path("/items/**")
                .filters(f ->
                    f.addResponseHeader("X-TestHeader", "foobar"))
                .uri("lb://ITEM-SERVICE")
            )
            .route(r -> r.path("/products/**")
                .filters(f ->
                    f.addResponseHeader("X-AnotherHeader", "baz"))
                .uri("lb://PRODUCT-SERVICE")
            )
            .build();
    }
}
