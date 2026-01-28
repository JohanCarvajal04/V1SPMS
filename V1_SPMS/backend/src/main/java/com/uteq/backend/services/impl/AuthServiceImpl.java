package com.uteq.backend.services.impl;

import com.uteq.backend.dtos.AuthResponse;
import com.uteq.backend.dtos.LoginRequest;
import com.uteq.backend.entities.UserCareerEntity;
import com.uteq.backend.entities.UsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.uteq.backend.repository.IUserCareerRepository;
import com.uteq.backend.repository.IUsersRepository;
import com.uteq.backend.services.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final IUsersRepository usersRepository;
    private final IUserCareerRepository userCareerRepository;
    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest req) {
        UsersEntity u = usersRepository.findByInstitutionalEmail(req.getInstitutionalEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        if (Boolean.FALSE.equals(u.getStatement())) {
            throw new RuntimeException("Usuario inactivo");
        }
        if (!req.getPassword().equals(u.getCredentials().getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        AuthResponse resp = new AuthResponse();
        resp.setIdUser(u.getId());
        resp.setNames(u.getNames());
        resp.setSurnames(u.getSurnames());
        resp.setInstitutionalEmail(u.getInstitutionalEmail());
        resp.setRole(u.getRole());
        if (!"ADMIN".equals(u.getRole())) {
            UserCareerEntity uc = userCareerRepository.findByUser_Id(u.getId())
                    .orElseThrow(() -> new RuntimeException("Usuario sin carrera asignada"));
            resp.setIdCareer(uc.getCareer().getId());
            resp.setCareerName(uc.getCareer().getCareerName());
            resp.setIdFaculty(uc.getCareer().getFaculty().getId());
            resp.setFacultyName(uc.getCareer().getFaculty().getFacultyName());
        }

        return resp;
    }
}
