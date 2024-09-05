package com.tli.user.domain.repositories;

import com.tli.user.domain.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    public Optional<UserEntity> findByEmail(String email);
}
