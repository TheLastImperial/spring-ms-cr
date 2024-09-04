package com.tli.address.api.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressRequest {
    @NotBlank(message = "You must set the street")
    private String street;
    @NotBlank(message = "You must set the city.")
    private String city;
    @NotBlank(message = "You must set the cp")
    private String zip;
}
