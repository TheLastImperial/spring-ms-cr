package com.tli.patient.infraestructure.services;

import com.tli.patient.api.models.request.PatientRequest;
import com.tli.patient.api.models.request.PhoneRequest;
import com.tli.patient.api.models.response.PatientResponse;
import com.tli.patient.api.models.response.PhoneResponse;
import com.tli.patient.domain.entities.PatientEntity;
import com.tli.patient.domain.entities.PhoneEntity;
import com.tli.patient.domain.repositories.PatientRepository;
import com.tli.patient.util.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Slf4j
@AllArgsConstructor
@Service
public class PatientService {
    private PatientRepository patientRepository;
    private PhoneService phoneService;
    private AddressService addressService;

    public PatientResponse create(PatientRequest patientRequest) {
        PatientEntity patientEntity = new PatientEntity();
        BeanUtils.copyProperties(patientRequest, patientEntity);
        PatientEntity savedPatient = patientRepository.save(patientEntity);
        savedPatient.setPhones(new HashSet<PhoneEntity>());
        for (PhoneRequest phone: patientRequest.getPhones()) {
            PhoneEntity phoneEntity = PhoneEntity.builder()
                    .patient(savedPatient)
                    .number(phone.getNumber())
                    .build();
            savedPatient.getPhones().add(phoneService.create(phoneEntity));
        }
        PatientResponse response =entityToResponse(savedPatient);
        addressService
                .create(patientRequest.getAddress())
                .doOnSuccess(addr->{
                    savedPatient.setAddress(addr.getId());
                    patientRepository.save(savedPatient);
                    response.setAddress(addr);
                })
                .doOnError(err->{
                    log.error("Patient creation error: {}", err);
                })
                .block();
        return response;
    }
    public PatientResponse update(PatientRequest patientRequest, Long id) {
        PatientEntity patientUpdate = patientRepository.findById(id).orElseThrow();
        PatientEntity patientUpdated = patientRepository.save(patientUpdate);
        return entityToResponse(patientUpdated);
    }
    public PatientResponse get(Long id) {
        log.info("Get patient by id: {}", id);
        PatientEntity patientEntity = patientRepository.findById(id).orElseThrow(()->{
            throw new NotFoundException("Patient not found");
        });

        PatientResponse response = entityToResponse(patientEntity);
        if(patientEntity.getAddress() != null){
            addressService.get(patientEntity.getAddress().toString())
                    .doOnSuccess(addr->{
                        response.setAddress(addr);
                    })
                    .block();
        }
        return response;
    }
    public void delete(Long id) {
        patientRepository.deleteById(id);
    }

    private PatientResponse entityToResponse(PatientEntity patientEntity) {
        PatientResponse patientResponse = new PatientResponse();
        BeanUtils.copyProperties(patientEntity, patientResponse);
        patientResponse.setPhones(new HashSet<PhoneResponse>());
        for(PhoneEntity phone: patientEntity.getPhones()) {
            patientResponse.getPhones().add(new PhoneResponse(phone.getNumber()));
        }
        return patientResponse;
    }
}
