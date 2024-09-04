package com.tli.user.services;

import com.tli.user.api.models.request.UserRequest;
import com.tli.user.api.models.response.UserResponse;
import com.tli.user.api.models.response.UserWithPasswordResponse;
import com.tli.user.domain.entities.RoleEntity;
import com.tli.user.domain.entities.UserEntity;
import com.tli.user.domain.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class UserService {
    private UserRepository userRepository;

    public UserResponse findByEmail(String email){
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(()-> {
                    log.error("Email not found {}", email);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
                });
        UserWithPasswordResponse response = UserWithPasswordResponse.builder().build();
        BeanUtils.copyProperties(user, response);
        return response;
    }

    public UserResponse create(UserRequest rq){
        UserEntity userToCreate = new UserEntity();
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(rq, userToCreate);
        if(userToCreate.getRoles() == null){
            userToCreate.setRoles(
                    List.of(RoleEntity.builder().id(Long.valueOf(3)).build())
            );
        }

        UserEntity userSaved = userRepository.save(userToCreate);
        BeanUtils.copyProperties(userSaved, response);
        return response;
    }

    public UserResponse get(Long id){
        UserEntity userEntity = userRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(userEntity, response);
        return response;
    }

}
