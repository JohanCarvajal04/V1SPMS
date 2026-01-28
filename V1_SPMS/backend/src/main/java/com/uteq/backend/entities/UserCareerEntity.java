package com.uteq.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_career",uniqueConstraints = @UniqueConstraint(columnNames = "usersiduser"))
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCareerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusercareer")
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usersiduser", nullable = false)
    private UsersEntity user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "careersidcareer", nullable = false)
    private CareerEntity career;

    @Column(name = "assigneddate", nullable = false)
    private LocalDateTime assignedDate;

    @Column(name = "active", nullable = false)
    private Boolean active;
}
