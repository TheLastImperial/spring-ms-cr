package com.tli.patient.api.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class PatientRequest {
    @NotBlank(message = "You must set the name.")
    private String name;
    @NotBlank(message = "You must set the father lastname")
    private String fatherLastName;
    @NotBlank(message = "You must set the mother lastname")
    private String motherLastName;
    private String gender;
    @NotBlank(message = "You must set the birthday")
    private String birthday;
    private String email;
    private AddressRequest address;
    private Set<PhoneRequest> phones;
}
