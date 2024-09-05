package com.tli.oauth2.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class FIndByEmail {
    private String email;
}
