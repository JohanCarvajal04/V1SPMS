package com.uteq.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CareerOptionResponse {
    private Integer idCareer;
    private String careerName;
    private Integer facultyId;
}
