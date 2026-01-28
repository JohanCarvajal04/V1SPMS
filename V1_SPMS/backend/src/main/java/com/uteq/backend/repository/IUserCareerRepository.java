package com.uteq.backend.repository;

import com.uteq.backend.entities.UserCareerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserCareerRepository extends JpaRepository<UserCareerEntity,Integer> {
    Optional<UserCareerEntity> findByUser_Id(Integer userId);

    boolean existsByUser_Id(Integer userId);

    List<UserCareerEntity> findByCareer_Id(Integer careerId);

    List<UserCareerEntity> findByCareer_Faculty_Id(Integer facultyId);
}
