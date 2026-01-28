package com.uteq.backend.services.impl;

import com.uteq.backend.dtos.DeanResponse;
import com.uteq.backend.entities.FacultyDeanEntity;
import com.uteq.backend.entities.FacultyEntity;
import com.uteq.backend.entities.UserCareerEntity;
import com.uteq.backend.entities.UsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.uteq.backend.repository.IFacultyDeanRepository;
import com.uteq.backend.repository.IFacultyRepository;
import com.uteq.backend.repository.IUserCareerRepository;
import com.uteq.backend.repository.IUsersRepository;
import com.uteq.backend.services.FacultyDeanService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FacultyDeanServiceImpl implements FacultyDeanService {
    private final IFacultyRepository facultyRepository;
    private final IUsersRepository usersRepository;
    private final IUserCareerRepository userCareerRepository;
    private final IFacultyDeanRepository facultyDeanRepository;

    @Override
    @Transactional
    public DeanResponse assignDean(Integer facultyId, Integer userId) {

        FacultyEntity faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new RuntimeException("Facultad no existe"));

        UsersEntity user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        if (!"DECANO".equals(user.getRole())) {
            throw new RuntimeException("El usuario no tiene rol DECANO");
        }

        UserCareerEntity uc = userCareerRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("El DECANO debe tener carrera asignada"));

        Integer userFacultyId = uc.getCareer().getFaculty().getId();
        if (!facultyId.equals(userFacultyId)) {
            throw new RuntimeException("El usuario no pertenece a la facultad seleccionada");
        }

        // Si ya hay decano activo, lo desactivamos y ponemos el nuevo
        facultyDeanRepository.findByFaculty_IdAndActiveTrue(facultyId).ifPresent(existing -> {
            existing.setActive(false);
            facultyDeanRepository.save(existing);
        });

        // Evitar que el mismo usuario sea decano en otra facultad
        if (facultyDeanRepository.existsByUser_IdAndActiveTrue(userId)) {
            throw new RuntimeException("El usuario ya es decano activo en otra facultad");
        }

        FacultyDeanEntity fd = new FacultyDeanEntity();
        fd.setFaculty(faculty);
        fd.setUser(user);
        fd.setAssignedDate(LocalDateTime.now());
        fd.setActive(true);
        facultyDeanRepository.save(fd);

        return mapDeanResponse(fd);
    }

    @Override
    @Transactional(readOnly = true)
    public DeanResponse getActiveDean(Integer facultyId) {
        FacultyDeanEntity fd = facultyDeanRepository.findByFaculty_IdAndActiveTrue(facultyId)
                .orElseThrow(() -> new RuntimeException("No hay decano activo para esta facultad"));

        return mapDeanResponse(fd);
    }

    private DeanResponse mapDeanResponse(FacultyDeanEntity fd) {
        DeanResponse r = new DeanResponse();
        r.setFacultyId(fd.getFaculty().getId());
        r.setFacultyName(fd.getFaculty().getFacultyName());
        r.setDeanUserId(fd.getUser().getId());
        r.setDeanNames(fd.getUser().getNames());
        r.setDeanSurnames(fd.getUser().getSurnames());
        r.setActive(fd.getActive());
        return r;
    }

}
