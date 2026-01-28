package com.uteq.backend.controllers;

import com.uteq.backend.dtos.ChangePasswordRequest;
import com.uteq.backend.dtos.UserCreateRequest;
import com.uteq.backend.dtos.UserResponse;
import com.uteq.backend.dtos.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.uteq.backend.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersControllers {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserCreateRequest req) {
        return ResponseEntity.ok(userService.createUser(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Integer id,
                                               @RequestBody UserUpdateRequest req) {
        return ResponseEntity.ok(userService.updateUser(id, req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Ejemplos:
     * /api/users?role=ESTUDIANTE
     * /api/users?facultyId=1
     * /api/users?facultyId=1&careerId=3&role=DECANO&active=true
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> list(
            @RequestParam(required = false) Integer facultyId,
            @RequestParam(required = false) Integer careerId,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) Boolean active
    ) {
        return ResponseEntity.ok(userService.listUsers(facultyId, careerId, role, active));
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<?> changePassword(@PathVariable Integer id,
                                            @RequestBody ChangePasswordRequest req) {
        userService.changePassword(id, req);
        return ResponseEntity.ok().build();
    }
}
