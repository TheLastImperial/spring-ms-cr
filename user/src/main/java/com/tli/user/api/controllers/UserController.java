package com.tli.user.api.controllers;

import com.tli.user.api.models.request.FindByEmail;
import com.tli.user.api.models.request.UserRequest;
import com.tli.user.api.models.response.UserResponse;
import com.tli.user.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest rq){
        return ResponseEntity.ok(userService.create(rq));
    }

    @PostMapping("/findByEmail")
    public ResponseEntity<UserResponse> findByEmail(@RequestBody FindByEmail findByEmail){
        log.info(findByEmail.getEmail());
        return ResponseEntity.ok(userService.findByEmail(findByEmail.getEmail()));
    }

    @GetMapping
    public ResponseEntity<UserResponse> getAll() {

        return ResponseEntity.ok(
                new UserResponse()
        );
    }
}
