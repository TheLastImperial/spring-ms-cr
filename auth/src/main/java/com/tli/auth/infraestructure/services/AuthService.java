package com.tli.auth.infraestructure.services;

import com.tli.auth.api.models.request.UserRequest;
import com.tli.auth.api.models.response.TokenResponse;
import com.tli.auth.domain.entities.UserEntity;
import com.tli.auth.domain.helpers.JwtHelper;
import com.tli.auth.domain.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@AllArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
//    private JwtHelper jwtHelper;

    private JwtService jwtService;
    public TokenResponse login(UserRequest userRequest) {
        UserEntity user = userRepository.findByEmail(userRequest.getEmail())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        validPassword(userRequest, user);
        return TokenResponse.builder()
                .accessToken(
//                        jwtHelper.createToken(userRequest.getEmail())
                        jwtService.generateToken(userRequest.getEmail())
                )
                .build();
    }

    public TokenResponse register(UserRequest userRequest){
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        UserEntity userToSave = new UserEntity();
        BeanUtils.copyProperties(userRequest, userToSave);
        userRepository.save(userToSave);
//        return TokenResponse.builder().accessToken(jwtHelper.createToken(userToSave.getEmail())).build();
        return TokenResponse
                .builder()
                .accessToken(
                        jwtService.generateToken(userToSave.getEmail())
                )
                .build();
    }


    public TokenResponse validateToken(String token) {
        return TokenResponse.builder().build();
    }

    public void validPassword(UserRequest userRequest, UserEntity userEntity) {
        if(passwordEncoder.matches(userRequest.getPassword(), userEntity.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
