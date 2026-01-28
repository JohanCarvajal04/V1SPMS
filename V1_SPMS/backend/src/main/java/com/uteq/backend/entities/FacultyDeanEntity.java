package com.uteq.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "faculty_dean",uniqueConstraints = {@UniqueConstraint(columnNames = "facultiesidfaculty"),@UniqueConstraint(columnNames = "usersiduser")})
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FacultyDeanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfacultydean")
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "facultiesidfaculty", nullable = false)
    private FacultyEntity faculty;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usersiduser", nullable = false)
    private UsersEntity user;

    @Column(name = "assigneddate", nullable = false)
    private LocalDateTime assignedDate;

    @Column(name = "active", nullable = false)
    private Boolean active;
}
