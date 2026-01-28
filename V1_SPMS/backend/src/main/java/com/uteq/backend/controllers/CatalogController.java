package com.uteq.backend.controllers;

import com.uteq.backend.dtos.CareerOptionResponse;
import com.uteq.backend.dtos.FacultyOptionResponse;
import com.uteq.backend.entities.CareerEntity;
import com.uteq.backend.entities.FacultyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.uteq.backend.repository.ICareerRepository;
import com.uteq.backend.repository.IFacultyRepository;

import java.util.List;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class CatalogController {
    private final IFacultyRepository facultyRepository;
    private final ICareerRepository careerRepository;

    @GetMapping("/faculties")
    public ResponseEntity<List<FacultyOptionResponse>> faculties(
            @RequestParam(required = false) Integer universityId
    ) {
        List<FacultyEntity> list = (universityId == null)
                ? facultyRepository.findAll()
                : facultyRepository.findByUniversityId(universityId);

        List<FacultyOptionResponse> resp = list.stream()
                .map(f -> new FacultyOptionResponse(f.getId(), f.getFacultyName()))
                .toList();

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/careers")
    public ResponseEntity<List<CareerOptionResponse>> careers(
            @RequestParam Integer facultyId
    ) {
        List<CareerEntity> list = careerRepository.findByFacultyId(facultyId);

        List<CareerOptionResponse> resp = list.stream()
                .map(c -> new CareerOptionResponse(
                        c.getId(),
                        c.getCareerName(),
                        c.getFaculty().getId()
                ))
                .toList();

        return ResponseEntity.ok(resp);
    }
}
