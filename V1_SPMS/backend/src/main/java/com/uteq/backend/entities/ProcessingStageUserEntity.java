package com.uteq.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "processingstageuser")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessingStageUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprocessingstageuser")
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usersiduser", nullable = false)
    private UsersEntity user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "processingstageidprocessingstage", nullable = false)
    private ProcessingStageEntity processingStage;

    @Column(name = "role", nullable = false, length = 100)
    private String role; // rol específico dentro de esa etapa

    @Column(name = "assigneddate", nullable = false)
    private LocalDateTime assignedDate;

    @Column(name = "active", nullable = false)
    private Boolean active;
}
