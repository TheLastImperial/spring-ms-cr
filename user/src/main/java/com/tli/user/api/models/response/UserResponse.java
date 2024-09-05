package com.tli.user.api.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@SuperBuilder
@Data
@NoArgsConstructor
public class UserResponse {
    private String name;
    private String email;
    private List<RoleResponse> roles;
}
