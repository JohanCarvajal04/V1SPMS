package com.uteq.backend.repository;

import com.uteq.backend.entities.ConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IConfigurationRepository extends JpaRepository<ConfigurationEntity, Integer> {

}
