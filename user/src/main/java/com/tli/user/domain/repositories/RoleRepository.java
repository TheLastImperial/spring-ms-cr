package com.tli.user.domain.repositories;

import com.tli.user.domain.entities.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    public Optional<List<RoleEntity>> findByIdIn(List<Long> ids);
}
