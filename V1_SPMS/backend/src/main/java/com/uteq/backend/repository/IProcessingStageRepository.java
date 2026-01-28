package com.uteq.backend.repository;

import com.uteq.backend.entities.ProcessingStageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProcessingStageRepository extends JpaRepository<ProcessingStageEntity,Integer> {
    List<ProcessingStageEntity> findByCreatedById(Integer userId);
}
