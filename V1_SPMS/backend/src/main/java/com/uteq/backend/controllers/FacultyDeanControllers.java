package com.uteq.backend.controllers;

import com.uteq.backend.dtos.AssignDeanRequest;
import com.uteq.backend.dtos.DeanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.uteq.backend.services.FacultyDeanService;

@RestController
@RequestMapping("/api/faculties")
@RequiredArgsConstructor
public class FacultyDeanControllers {
    private final FacultyDeanService facultyDeanService;

    @PostMapping("/{facultyId}/dean")
    public ResponseEntity<DeanResponse> assignDean(@PathVariable Integer facultyId,
                                                   @RequestBody AssignDeanRequest req) {
        return ResponseEntity.ok(facultyDeanService.assignDean(facultyId, req.getUserId()));
    }

    @GetMapping("/{facultyId}/dean")
    public ResponseEntity<DeanResponse> getDean(@PathVariable Integer facultyId) {
        return ResponseEntity.ok(facultyDeanService.getActiveDean(facultyId));
    }
}
