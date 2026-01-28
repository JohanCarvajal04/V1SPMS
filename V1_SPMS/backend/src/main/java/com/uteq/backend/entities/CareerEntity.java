package com.uteq.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "careers")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CareerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcareer")
    private Integer id;

    @Column(name = "careername", nullable = false, length = 255)
    private String careerName;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "facultiesidfaculty", nullable = false)
    private FacultyEntity faculty;

    // Responsable/coordinador/creador de la carrera (según tu negocio)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usersiduser", nullable = false)
    private UsersEntity responsibleUser;
}
