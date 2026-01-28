package com.uteq.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignDeanRequest {
    private Integer userId; // usuario con role = DECANO
}
