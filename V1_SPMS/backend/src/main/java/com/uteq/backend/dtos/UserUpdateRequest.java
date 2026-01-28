package com.uteq.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    private String names;
    private String surnames;

    private String personalMail;

    private String role;        // cuidado: si cambias a ADMIN, debes quitar user_career
    private Boolean statement;

    // si cambia de carrera (solo para no-admin)
    private Integer careerId;

    // actualizar canales de config
    private Boolean smsChannel;
    private Boolean mailChannel;
    private Boolean whatsappChannel;
}
