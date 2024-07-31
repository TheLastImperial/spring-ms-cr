package com.tli.patient.api.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PhoneRequest {
    @NotBlank(message = "You must set the phone number")
    private String number;
    private Long patientId;
}
