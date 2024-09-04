package com.tli.gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

// Tambien se puede usar LoadBalancerClientConfiguration como configuracion por defecto.
@Configuration
@LoadBalancerClient(
        name="oauth2",
        configuration = LoadBalancerConfig.class
)
public class WebClientConfig {
    @Bean
    @LoadBalanced
    public WebClient.Builder getWebClient(){
        return WebClient.builder();
    }
}
