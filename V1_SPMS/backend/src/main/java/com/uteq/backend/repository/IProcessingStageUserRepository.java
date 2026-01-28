package com.uteq.backend.repository;

import com.uteq.backend.entities.ProcessingStageUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IProcessingStageUserRepository extends JpaRepository<ProcessingStageUserEntity,Integer> {

    List<ProcessingStageUserEntity> findByUserId(Integer userId);

    List<ProcessingStageUserEntity> findByProcessingStageId(Integer processingStageId);

    Optional<ProcessingStageUserEntity> findByProcessingStageIdAndUserId(Integer processingStageId, Integer userId);

    List<ProcessingStageUserEntity> findByProcessingStageIdAndActiveTrue(Integer processingStageId);

    List<ProcessingStageUserEntity> findByUserIdAndActiveTrue(Integer userId);

}
