package com.uteq.backend.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "credentials")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcredentials")
    private Integer id;

    @Column(name = "password", nullable = false, columnDefinition = "text")
    private String password;

    @Column(name = "datemodification")
    private LocalDate dateModification;

}
