package com.tli.patient.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
@Entity(name="patients")
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String fatherLastName;
    private String motherLastName;
    private String gender;
    private String birthday;
    private String email;
    private Long address;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "patient"
    )
//    @JoinColumn(name="patient_id")
    private Set<PhoneEntity> phones;
}
