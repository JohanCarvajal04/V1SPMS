package com.uteq.backend.services;

import com.uteq.backend.dtos.AuthResponse;
import com.uteq.backend.dtos.LoginRequest;

public interface AuthService {
    AuthResponse login(LoginRequest req);
}
