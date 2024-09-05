package com.tli.oauth2.api.models.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginUserRequest {
    private String email;
    private String password;
}
