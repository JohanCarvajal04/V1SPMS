package com.uteq.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private Integer idUser;
    private String names;
    private String surnames;
    private String institutionalEmail;
    private String role;
    private Integer idFaculty;
    private String facultyName;
    private Integer idCareer;
    private String careerName;
}
