package com.tli.oauth2.api.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@Data
@NoArgsConstructor
public class UserResponse {
    private String name;
    private String email;
}
