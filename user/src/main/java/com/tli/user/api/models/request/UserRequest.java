package com.tli.user.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class UserRequest {
    private String email;
    private String name;
    private String password;
    private List<Long> roles;
}
