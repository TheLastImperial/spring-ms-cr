package com.tli.gateway.filters;

import com.tli.gateway.dto.TokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@Profile("eureka-off")
public class AuthHttpFilter implements GatewayFilter {
    private WebClient webClient;
    private String oauth2Url;

    public AuthHttpFilter(
            @Value("${application.microservices.oauth2.host}${application.microservices.oauth2.path}")
            String oauth2Url
    ) {
        this.oauth2Url = oauth2Url;
        this.webClient = WebClient.builder()
                .baseUrl(this.oauth2Url)
                .build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if(!exchange.getRequest().getHeaders().containsKey("Authorization")) {
            return onError(exchange);
        }

        String tokenHeader = exchange
                .getRequest()
                .getHeaders()
                .getFirst("Authorization");

        String[] chunks = tokenHeader.split(" ");

        if(chunks.length != 2 || !"Bearer".equals(chunks[0])) {
            return onError(exchange);
        }

        TokenDto token = TokenDto
                .builder()
                .token(chunks[1])
                .build();

        return webClient
                .post()
                .uri("/validate")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, tokenHeader)
                .bodyValue(token)
                .retrieve()
                .bodyToMono(TokenDto.class)
                .onErrorMap(Exception.class, ex -> ex)
                .onErrorResume(WebClientResponseException.class, ex -> Mono.error(ex))
                .map(response -> exchange)
                .flatMap(chain::filter);
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        return response.setComplete();
    }
}
