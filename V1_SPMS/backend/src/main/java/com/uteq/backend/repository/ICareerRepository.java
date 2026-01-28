package com.uteq.backend.repository;

import com.uteq.backend.entities.CareerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICareerRepository extends JpaRepository<CareerEntity,Integer> {
    List<CareerEntity> findByFacultyId(Integer facultyID);
}
