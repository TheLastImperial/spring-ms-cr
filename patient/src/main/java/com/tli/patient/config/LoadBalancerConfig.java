package com.tli.patient.config;

import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

public class LoadBalancerConfig {
    @Bean
    public ServiceInstanceListSupplier serviceClient(
            ConfigurableApplicationContext ctx
    ){
        return ServiceInstanceListSupplier
                .builder()
                .withDiscoveryClient()
                .withWeighted()
                .withCaching()
                .build(ctx);
    }
}
