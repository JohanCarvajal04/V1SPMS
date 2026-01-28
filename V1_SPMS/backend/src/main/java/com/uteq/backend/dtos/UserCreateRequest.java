package com.uteq.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    private String names;
    private String surnames;
    private String cardId;

    private String institutionalEmail;
    private String personalMail;

    private String role;        // ADMIN | DECANO | COORDINADOR | ESTUDIANTE
    private Boolean statement;  // activo/inactivo

    private String password;

    // Obligatorio si role != ADMIN
    private Integer careerId;

    // Config por defecto (puedes setearlo en backend si no viene)
    private Boolean smsChannel;
    private Boolean mailChannel;
    private Boolean whatsappChannel;
}
