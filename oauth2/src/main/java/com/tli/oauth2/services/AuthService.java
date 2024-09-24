package com.tli.oauth2.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tli.oauth2.api.models.request.CreateUserRequest;
import com.tli.oauth2.api.models.request.LoginUserRequest;
import com.tli.oauth2.api.models.request.TokenDto;
import com.tli.oauth2.api.models.response.UserResponse;
import com.tli.oauth2.api.models.response.UserWithPasswordResponse;
import com.tli.oauth2.services.interfaces.IUserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Slf4j
@Service
public class AuthService {
    private BCryptPasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private IUserService userService;

    public AuthService(BCryptPasswordEncoder passwordEncoder, JwtService jwtService,
                       IUserService userService
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
                .token(jwtService.generateToken(userToMap(response), response.getEmail()))
                .build();
    }

    public TokenDto login(LoginUserRequest rq){
        UserWithPasswordResponse user = userService.findbyEmail(rq.getEmail()).block();

        if(!passwordEncoder.matches(rq.getPassword(), user.getPassword())){
            log.error("Password incorrecta para {}", rq.getEmail());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        return TokenDto.builder()
                .token(
                        jwtService.generateToken(userToMap(userResponse), user.getEmail())
                )
                .build();
    }

    public TokenDto validate(String token){
        if(!jwtService.validate(token)){
            log.error("Token invalido");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return TokenDto.builder().token(token).build();
    }
    private Map<String, Object> userToMap(UserResponse user){
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(user, Map.class);
    }
}
