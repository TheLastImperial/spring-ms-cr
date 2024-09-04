package com.tli.oauth2.api.controllers;

import com.tli.oauth2.api.models.request.CreateUserRequest;
import com.tli.oauth2.api.models.request.LoginUserRequest;
import com.tli.oauth2.api.models.request.TokenDto;
import com.tli.oauth2.services.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<TokenDto> register( @RequestBody CreateUserRequest rq){
        return ResponseEntity.ok(authService.register(rq));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginUserRequest rq){
        return ResponseEntity.ok(authService.login(rq));
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(@RequestBody TokenDto token){
        return ResponseEntity.ok(authService.validate(token.getToken()));
    }
}
