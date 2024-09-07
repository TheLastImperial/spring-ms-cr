package com.tli.patient.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@LoadBalancerClient(
        name="address",
        configuration = LoadBalancerConfig.class
)
public class AddressWebClientConfig {
    @Bean
    @LoadBalanced
    @Profile("eureka-on")
    public WebClient.Builder webClientBuilderBalanced() {
        return WebClient.builder();
    }

    @Bean
    @Profile("eureka-off")
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
