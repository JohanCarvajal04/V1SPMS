package com.uteq.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Integer idUser;
    private String names;
    private String surnames;
    private String cardId;

    private String institutionalEmail;
    private String personalMail;

    private String role;
    private Boolean statement;

    // Config (canales)
    private Boolean smsChannel;
    private Boolean mailChannel;
    private Boolean whatsappChannel;

    // Para NO admin
    private Integer idFaculty;
    private String facultyName;
    private Integer idCareer;
    private String careerName;
}
