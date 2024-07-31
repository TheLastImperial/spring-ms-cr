package com.tli.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(route -> route
                        .path("/patients/**")
                        .filters(filter->
                                filter.prefixPath("/ms-patients")
                        )
                        .uri("http://localhost:8081")
                )
                .route(route-> route
                        .path("/addresses/**")
                        .filters(filter->
                                filter.prefixPath("/ms-addresses")
                        )
                        .uri("http://localhost:8082/")
                )
                .route(route-> route
                        .path("/test/**")
                        .filters(f -> {
                            log.info("Entre en el test");
                            return f.addRequestHeader("Hello", "World");
                        })
                        .uri("https://httpbin.org")
                )
                .build();
    }
}
