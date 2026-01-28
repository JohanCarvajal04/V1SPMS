package com.uteq.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "processingstage")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessingStageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprocessingstage")
    private Integer id;

    @Column(name = "stagename", nullable = false, length = 255)
    private String stageName;

    @Column(name = "stagedescription", columnDefinition = "text")
    private String stageDescription;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usersiduser", nullable = false)
    private UsersEntity createdBy;
}
