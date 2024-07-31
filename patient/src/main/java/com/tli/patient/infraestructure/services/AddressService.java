package com.tli.patient.infraestructure.services;

import com.tli.patient.api.models.request.AddressRequest;
import com.tli.patient.api.models.response.AddressResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Slf4j
@Service
public class AddressService {
    private WebClient webClient;
    public AddressService() {
        log.info("AddressService instantiated");
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8081")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(logRequest())
                .filter((request, next) -> next.exchange(request)
                        .onErrorResume(
                                WebClientResponseException.class, ex -> {
                                    log.error(ex.getMessage());
                                    return Mono.empty();
//                                    ex.getRawStatusCode() == 418 ? Mono.empty() : Mono.error(ex)
                                })
                )
                .build();
    }

    public Mono<AddressResponse> create(AddressRequest addressRequest) {
        return webClient.post().uri("/addresses").bodyValue(addressRequest)
                .retrieve()
                .bodyToMono(AddressResponse.class);
    }
    public Mono<AddressResponse> get(String id) {
        return webClient.get().uri("/addresses/{id}", id).retrieve().bodyToMono(AddressResponse.class);
    }
    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            log.info("Request: {} {}" + clientRequest.method() + clientRequest.url());
            clientRequest.headers()
                    .forEach((name, values) -> values.forEach(value -> log.info("{}={}" + name + value)));
            return next.exchange(clientRequest);
        };
    }
}
