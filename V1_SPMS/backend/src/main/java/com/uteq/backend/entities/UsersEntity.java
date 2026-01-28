package com.uteq.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iduser")
    private Integer id;

    @Column(name = "names", nullable = false, length = 255)
    private String names;

    @Column(name = "surnames", nullable = false, length = 255)
    private String surnames;

    @Column(name = "cardid", nullable = false, unique = true, length = 10)
    private String cardId;

    @Column(name = "institutionalemail", nullable = false, unique = true, length = 255)
    private String institutionalEmail;

    @Column(name = "personalmail", unique = true, length = 255)
    private String personalMail;

    @Column(name = "statement", nullable = false)
    private Boolean statement; // activo/inactivo (según tu lógica)

    @Column(name = "role", nullable = false, length = 255)
    private String role; // si luego normalizas roles, esto se vuelve FK

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "configurationsidconfiguration", nullable = false)
    private ConfigurationEntity configurations;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "credentialsidcredentials", nullable = false)
    private CredentialsEntity credentials;
}
