package com.uteq.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idstudent")
    private Integer id;

    @Column(name = "semester", nullable = false, length = 255)
    private String semester;

    @Column(name = "parallel", nullable = false, length = 1)
    private String parallel;

    @Column(name = "phonenumber", length = 10)
    private String phoneNumber;

    @Column(name = "externalstudents", nullable = false)
    private Boolean externalStudents;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usersiduser", nullable = false)
    private UsersEntity user;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "careersidcareer", nullable = false)
    private CareerEntity career;
}
