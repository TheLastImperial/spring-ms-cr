package com.tli.oauth2.services;

import com.tli.oauth2.api.models.request.CreateUserRequest;
import com.tli.oauth2.api.models.request.FIndByEmail;
import com.tli.oauth2.api.models.response.UserResponse;
import com.tli.oauth2.api.models.response.UserWithPasswordResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UserService {
    private String userUrl;
    private WebClient userWebClient;

    public UserService(WebClient.Builder webClient,
        @Value("${application.microservices.user}") String userUrl
    ){
        this.userUrl = userUrl;
        this.userWebClient = webClient
                .baseUrl(this.userUrl)
                .filter((request, next) -> next.exchange(request)
                    .onErrorResume(
                        WebClientResponseException.class, ex -> {
                            log.error(ex.getMessage());
                            return Mono.empty();
                    })
                )
                .build();
    }

    public Mono<UserResponse> create(CreateUserRequest rq){
        log.info("Creando usuario");
        return userWebClient.post()
                .uri("/users")
                .bodyValue(rq)
                .retrieve()
                .bodyToMono(UserResponse.class);
    }

    public Mono<UserWithPasswordResponse> findbyEmail(String email){
        log.info("Buscando usuario");
        return userWebClient.post()
                .uri("/users/findByEmail")
                .bodyValue(FIndByEmail.builder().email(email).build())
                .retrieve()
                .bodyToMono(UserWithPasswordResponse.class);
    }
}
