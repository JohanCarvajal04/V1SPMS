package com.uteq.backend.services;

import com.uteq.backend.dtos.ChangePasswordRequest;
import com.uteq.backend.dtos.UserCreateRequest;
import com.uteq.backend.dtos.UserResponse;
import com.uteq.backend.dtos.UserUpdateRequest;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreateRequest req);

    UserResponse updateUser(Integer userId, UserUpdateRequest req);

    UserResponse getUserById(Integer userId);

    List<UserResponse> listUsers(Integer facultyId, Integer careerId, String role, Boolean active);

    void changePassword(Integer userId, ChangePasswordRequest req);
}
