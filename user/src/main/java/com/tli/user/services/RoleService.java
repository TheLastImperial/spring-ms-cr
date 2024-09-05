package com.tli.user.services;

import com.tli.user.api.models.request.RoleRequest;
import com.tli.user.domain.entities.RoleEntity;
import com.tli.user.domain.repositories.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    public RoleEntity create(RoleRequest rq){
        RoleEntity roleToSave = RoleEntity.builder().build();
        BeanUtils.copyProperties(rq, roleToSave);
        RoleEntity roleSaved = roleRepository.save(roleToSave);
        return roleSaved;
    }
}
