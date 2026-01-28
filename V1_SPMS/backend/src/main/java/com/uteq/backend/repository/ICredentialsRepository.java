package com.uteq.backend.repository;

import com.uteq.backend.entities.CredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICredentialsRepository extends JpaRepository<CredentialsEntity, Integer> {

}
