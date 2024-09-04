package com.tli.oauth2.services;

import com.tli.oauth2.api.models.request.CreateUserRequest;
import com.tli.oauth2.api.models.request.LoginUserRequest;
import com.tli.oauth2.api.models.request.TokenDto;
import com.tli.oauth2.api.models.response.UserResponse;
import com.tli.oauth2.api.models.response.UserWithPasswordResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
public class AuthService {
    private BCryptPasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private UserService userService;

    public AuthService(BCryptPasswordEncoder passwordEncoder, JwtService jwtService,
                       UserService userService
    ){
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public TokenDto register(CreateUserRequest rq){
        rq.setPassword(passwordEncoder.encode(rq.getPassword()));
        UserResponse response = userService.create(rq).block();

        return TokenDto
                .builder()
                .token(jwtService.generateToken(response.getEmail()))
                .build();
    }

    public TokenDto login(LoginUserRequest rq){
        UserWithPasswordResponse user = userService.findbyEmail(rq.getEmail()).block();

        if(!passwordEncoder.matches(rq.getPassword(), user.getPassword())){
            log.error("Password incorrecta para {}", rq.getEmail());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return TokenDto.builder()
                .token(jwtService.generateToken(user.getEmail()))
                .build();
    }

    public TokenDto validate(String token){
        if(!jwtService.validate(token)){
            log.error("Token invalido");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return TokenDto.builder().token(token).build();
    }

}
