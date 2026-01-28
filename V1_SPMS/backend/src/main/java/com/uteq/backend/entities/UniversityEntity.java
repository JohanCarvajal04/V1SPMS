package com.uteq.backend.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "university")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UniversityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iduniversity")
    private Integer id;

    @Column(name = "universityname", nullable = false, length = 255)
    private String universityName;

    @Column(name = "publicuniversity", nullable = false)
    private Boolean publicUniversity;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "phonenumber", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "province", nullable = false, length = 100)
    private String province;

    @Column(name = "country", nullable = false, length = 100)
    private String country;
}
