package com.tli.oauth2.services.interfaces;

import com.tli.oauth2.api.models.request.CreateUserRequest;
import com.tli.oauth2.api.models.response.UserResponse;
import com.tli.oauth2.api.models.response.UserWithPasswordResponse;

import reactor.core.publisher.Mono;

public interface IUserService {
    public Mono<UserResponse> create(CreateUserRequest rq);
    public Mono<UserWithPasswordResponse> findbyEmail(String email);
}
