package com.uteq.backend.repository;

import com.uteq.backend.entities.FacultyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFacultyRepository extends JpaRepository<FacultyEntity,Integer> {
    List<FacultyEntity> findByUniversityId(Integer universityId);
}
