package com.tli.auth.api.controllers;

import com.tli.auth.api.models.request.UserRequest;
import com.tli.auth.api.models.response.TokenResponse;
import com.tli.auth.infraestructure.services.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(path="/auth")
public class AuthController {
    private AuthService authService;

    @PostMapping(path="/register")
    public ResponseEntity<TokenResponse> register(UserRequest userRequest, UriComponentsBuilder ucb) {
        log.info("User: {}", userRequest);
        URI location = ucb.path("/register")
                .buildAndExpand("").toUri();

        return ResponseEntity.created(location)
                .body(authService.register(userRequest));
    }

    @PostMapping(path="/login")
    public ResponseEntity<TokenResponse> login(UserRequest userRequest) {
        return ResponseEntity.ok(authService.login(userRequest));
    }
}
