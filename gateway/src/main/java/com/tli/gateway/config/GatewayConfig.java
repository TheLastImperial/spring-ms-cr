package com.tli.gateway.config;

import com.tli.gateway.filters.AuthFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@AllArgsConstructor
@Slf4j
@Configuration
public class GatewayConfig {
    private AuthFilter authFilter;

    @Bean
    @Profile(value = "eureka-on")
    public RouteLocator routeLocatorEureka(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route( route -> route
                        .path("/patients/**")
                        .filters(filter-> {
                            filter.filter(authFilter);
                            filter.prefixPath("/ms-patients");
                            return filter;
                        })
                        .uri("lb://patient")
                )
                .route( route-> route
                        .path("/addresses/**")
                        .filters(filter->
                                filter.prefixPath("/ms-addresses")
                        )
                        .uri("lb://address")
                )
                .route( route -> route
                        .path("/users/**")
                        .filters(filter -> {
                            filter.prefixPath("/ms-users");
                            return filter;
                        })
                        .uri("lb://user")
                )
                .route( route -> route
                        .path("/auth/**")
                        .filters( filter -> {
                            filter.prefixPath("/ms-oauth2");
                            return filter;
                        })
                        .uri("lb://oauth2")
                )
                .route(route -> route
                        .path("/files/**")
                        .filters(filter -> {
                                filter.rewritePath("/files", "/");
                                filter.prefixPath("/rails/active_storage");
                                return filter;
                        })
                        .uri("lb://storage")
                )
                .build();
    }
}
