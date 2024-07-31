package com.tli.address.services;

import com.tli.address.api.models.request.AddressRequest;
import com.tli.address.api.models.request.UpdateAddressRequest;
import com.tli.address.domain.entities.AddressEntity;
import com.tli.address.domain.repositories.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {
    private AddressRepository addressRepository;
    public AddressEntity read(Long id) {
        return addressRepository.findById(id).get();
    }
    public AddressEntity create(AddressRequest address) {
        AddressEntity toSave = new AddressEntity();
        BeanUtils.copyProperties(address, toSave);
        return addressRepository.save(toSave);
    }
    public void delete(Long id) {
        addressRepository.deleteById(id);
    }
    public AddressEntity update(UpdateAddressRequest addressUpdate, Long id) {
        AddressEntity address = addressRepository.findById(id).get();
        BeanUtils.copyProperties(addressUpdate, address);
        return addressRepository.save(address);
    }
}
