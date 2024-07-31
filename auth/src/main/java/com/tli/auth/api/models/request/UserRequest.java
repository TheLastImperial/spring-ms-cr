package com.tli.auth.api.models.request;

import lombok.Data;

@Data
public class UserRequest {
    private String email;
    private String password;
}
