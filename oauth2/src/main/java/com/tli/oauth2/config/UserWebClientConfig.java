package com.tli.oauth2.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@LoadBalancerClient(name="user")
public class UserWebClientConfig {
    @Bean
    @LoadBalanced
    public WebClient.Builder userWebClient(){
        return WebClient.builder();
    }
}
