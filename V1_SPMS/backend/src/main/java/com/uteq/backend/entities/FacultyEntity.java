package com.uteq.backend.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "faculties")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FacultyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfaculty")
    private Integer id;

    @Column(name = "facultyname", nullable = false, length = 255)
    private String facultyName;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "universityiduniversity", nullable = false)
    private UniversityEntity university;

    // En tu BD es un FK obligatorio: puede ser el responsable/decano/creador
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usersiduser", nullable = false)
    private UsersEntity responsibleUser;
}
