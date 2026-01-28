package com.uteq.backend.repository;

import com.uteq.backend.entities.FacultyDeanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IFacultyDeanRepository extends JpaRepository<FacultyDeanEntity,Integer> {
    Optional<FacultyDeanEntity> findByFaculty_IdAndActiveTrue(Integer facultyId);

    boolean existsByFaculty_IdAndActiveTrue(Integer facultyId);

    boolean existsByUser_IdAndActiveTrue(Integer userId);
}
