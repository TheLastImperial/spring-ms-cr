package com.tli.patient.api.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse  implements Serializable {
    private Long id;
    private String name;
    private String fatherLastName;
    private String motherLastName;
    private String gender;
    private String birthday;
    private String email;
    private AddressResponse address;
    private Set<PhoneResponse> phones;
}
