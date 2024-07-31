package com.tli.address.api.models.request;

import lombok.Data;

@Data
public class UpdateAddressRequest {
    private String street;
    private String city;
    private String cp;
}
