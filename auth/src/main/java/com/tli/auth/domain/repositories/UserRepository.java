package com.tli.auth.domain.repositories;

import com.tli.auth.domain.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    public Optional<UserEntity> findByEmail(String email);
}
