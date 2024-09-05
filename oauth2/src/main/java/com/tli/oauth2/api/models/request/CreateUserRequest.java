package com.tli.oauth2.api.models.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateUserRequest {
    private String name;
    private String email;
    private String password;
}
