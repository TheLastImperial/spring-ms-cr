package com.tli.patient.infraestructure.services;

import com.tli.patient.api.models.request.PhoneRequest;
import com.tli.patient.domain.entities.PhoneEntity;
import com.tli.patient.domain.repositories.PhoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PhoneService {
    private PhoneRepository phoneRepository;
    public PhoneEntity create(PhoneEntity phoneEntity) {
        return phoneRepository.save(phoneEntity);
    }
    public PhoneEntity update(PhoneRequest phoneRequest, Long id) {
        PhoneEntity phoneToUpdate = phoneRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(phoneRequest, phoneToUpdate);
        return phoneRepository.save(phoneToUpdate);
    }
    public void delete(Long id) {
        phoneRepository.deleteById(id);
    }
    public PhoneEntity get(Long id) {
        return phoneRepository.findById(id).orElse(null);
    }
}
