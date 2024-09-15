package com.tli.patient.domain.repositories;

import com.tli.patient.domain.entities.PatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends CrudRepository<PatientEntity, Long>,
        PagingAndSortingRepository<PatientEntity, Long> {
    Page<PatientEntity> findByNameIgnoreCaseContainingOrFatherLastNameIgnoreCaseContainingOrMotherLastNameIgnoreCaseContaining(
            String name, String fatherLastName, String motherLastName, Pageable page);
}
