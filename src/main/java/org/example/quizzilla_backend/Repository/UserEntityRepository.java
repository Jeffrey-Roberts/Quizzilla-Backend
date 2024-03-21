package org.example.quizzilla_backend.Repository;

import org.example.quizzilla_backend.Model.UserEntity;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserEntityRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByUsernameIgnoreCase(String username);
}