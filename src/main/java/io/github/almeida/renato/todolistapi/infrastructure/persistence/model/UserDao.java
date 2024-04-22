package io.github.almeida.renato.todolistapi.infrastructure.persistence.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserDao extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByUsername(String username);
}
