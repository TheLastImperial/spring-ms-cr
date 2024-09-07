package com.tli.gateway.config;

import com.tli.gateway.filters.AuthEurekaFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Configuration
public class GatewayConfig {
    private GatewayFilter authFilter;

    @Value("${application.microservices.oauth2.host}")
    private String oauth2Host;
    @Value("${application.microservices.user.host}")
    private String userHost;
    @Value("${application.microservices.address.host}")
    private String addressHost;
    @Value("${application.microservices.patient.host}")
    private String patientHost;

    public GatewayConfig(GatewayFilter authFilter){
        this.authFilter = authFilter;
    }
    @Bean
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
                        .uri(patientHost)
                )
                .route( route-> route
                        .path("/addresses/**")
                        .filters(filter->
                                filter.prefixPath("/ms-addresses")
                        )
                        .uri(addressHost)
                )
                .route( route -> route
                        .path("/users/**")
                        .filters(filter -> {
                            filter.prefixPath("/ms-users");
                            return filter;
                        })
                        .uri(oauth2Host)
                )
                .route( route -> route
                        .path("/auth/**")
                        .filters( filter -> {
                            filter.prefixPath("/ms-oauth2");
                            return filter;
                        })
                        .uri(oauth2Host)
                )
                .build();
    }

}
