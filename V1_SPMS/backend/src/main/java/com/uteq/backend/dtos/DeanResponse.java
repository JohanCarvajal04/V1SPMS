package com.uteq.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeanResponse {
    private Integer facultyId;
    private String facultyName;

    private Integer deanUserId;
    private String deanNames;
    private String deanSurnames;

    private Boolean active;
}
