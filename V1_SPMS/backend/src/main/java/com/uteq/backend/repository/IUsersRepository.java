package com.uteq.backend.repository;

import com.uteq.backend.entities.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUsersRepository extends JpaRepository<UsersEntity, Integer> {
    Optional<UsersEntity> findByInstitutionalEmail(String institutionalEmail);

    Optional<UsersEntity> findByCardId(String cardId);

    boolean existsByInstitutionalEmail(String institutionalEmail);

    boolean existsByCardId(String cardId);

    List<UsersEntity> findByRole(String role);

    List<UsersEntity> findByStatement(Boolean statement); // activo/inactivo
}
