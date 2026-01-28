package com.uteq.backend.repository;

import com.uteq.backend.entities.StudentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IStudentsRepository extends JpaRepository<StudentsEntity,Integer> {

    Optional<StudentsEntity> findByUser_Id(Integer userId);

    boolean existsByUser_Id(Integer userId);

    List<StudentsEntity> findByCareer_Id(Integer careerId);

    List<StudentsEntity> findByCareer_Faculty_Id(Integer facultyId);
}
