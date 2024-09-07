package com.tli.oauth2.services;

import com.tli.oauth2.api.models.request.CreateUserRequest;
import com.tli.oauth2.api.models.request.FIndByEmail;
import com.tli.oauth2.api.models.response.UserResponse;
import com.tli.oauth2.api.models.response.UserWithPasswordResponse;
import com.tli.oauth2.services.interfaces.IUserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Profile("eureka-on")
public class UserEurekaService implements IUserService{
    private String userUrl;
    private WebClient userWebClient;

    public UserEurekaService(WebClient.Builder webClient,
        @Value("${application.microservices.user.host}${application.microservices.user.path}")
        String userUrl
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
