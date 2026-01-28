package com.uteq.backend.repository;

import com.uteq.backend.entities.UniversityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUniversityRepository extends JpaRepository<UniversityEntity, Integer> {

}
