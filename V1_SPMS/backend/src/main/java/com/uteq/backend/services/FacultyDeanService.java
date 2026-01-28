package com.uteq.backend.services;

import com.uteq.backend.dtos.DeanResponse;

public interface FacultyDeanService {
    DeanResponse assignDean(Integer facultyId, Integer userId);

    DeanResponse getActiveDean(Integer facultyId);
}
