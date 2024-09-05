package com.tli.patient.api.models.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressResponse implements Serializable {
    private Long id;
    private String street;
    private String city;
    private String zip;
}
