package com.uteq.backend.services.impl;

import com.uteq.backend.dtos.ChangePasswordRequest;
import com.uteq.backend.dtos.UserCreateRequest;
import com.uteq.backend.dtos.UserResponse;
import com.uteq.backend.dtos.UserUpdateRequest;
import com.uteq.backend.entities.*;

import com.uteq.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.uteq.backend.services.UserService;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final IUsersRepository usersRepository;
    private final ICredentialsRepository credentialsRepository;
    private final IConfigurationRepository configurationRepository;
    private final ICareerRepository careerRepository;
    private final IUserCareerRepository userCareerRepository;


    private static final Set<String> ROLES =Set.of("ADMIN", "DECANO", "COORDINADOR", "ESTUDIANTE");


    @Override
    @Transactional
    public UserResponse createUser(UserCreateRequest req) {
        validateRole(req.getRole());

        // Validaciones de duplicados
        if (usersRepository.existsByInstitutionalEmail(req.getInstitutionalEmail())) {
            throw new RuntimeException("Ya existe un usuario con ese institutionalEmail");
        }
        if (usersRepository.existsByCardId(req.getCardId())) {
            throw new RuntimeException("Ya existe un usuario con esa cédula/cardId");
        }

        // Regla carrera obligatoria excepto ADMIN
        if (!"ADMIN".equals(req.getRole())) {
            if (req.getCareerId() == null) throw new RuntimeException("careerId es obligatorio para rol " + req.getRole());
        } else {
            if (req.getCareerId() != null) throw new RuntimeException("ADMIN no debe tener careerId");
        }

        // 1) Crear Credentials
        CredentialsEntity cred = new CredentialsEntity();
        cred.setPassword(req.getPassword());
        cred.setDateModification(LocalDate.now());
        cred = credentialsRepository.save(cred);

        // 2) Crear Configuration
        ConfigurationEntity conf = new ConfigurationEntity();
        conf.setProfilePicture(null);
        conf.setScannedSignature(null);
        conf.setSmsChannel(Boolean.TRUE.equals(req.getSmsChannel()));
        conf.setMailChannel(Boolean.TRUE.equals(req.getMailChannel()));
        conf.setWhatsappChannel(Boolean.TRUE.equals(req.getWhatsappChannel()));
        conf = configurationRepository.save(conf);

        // 3) Crear Users
        UsersEntity u = new UsersEntity();
        u.setNames(req.getNames());
        u.setSurnames(req.getSurnames());
        u.setCardId(req.getCardId());
        u.setInstitutionalEmail(req.getInstitutionalEmail());
        u.setPersonalMail(req.getPersonalMail());
        u.setRole(req.getRole());
        u.setStatement(req.getStatement() != null ? req.getStatement() : Boolean.TRUE);

        u.setCredentials(cred);
        u.setConfigurations(conf);

        u = usersRepository.save(u);

        // 4) Asignación user_career si no es ADMIN
        UserCareerEntity uc = null;
        if (!"ADMIN".equals(req.getRole())) {
            CareerEntity career = careerRepository.findById(req.getCareerId())
                    .orElseThrow(() -> new RuntimeException("careerId no existe"));

            uc = new UserCareerEntity();
            uc.setUser(u);
            uc.setCareer(career);
            uc.setAssignedDate(LocalDateTime.now());
            uc.setActive(true);

            userCareerRepository.save(uc);
        }

        return mapToUserResponse(u, uc);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Integer userId, UserUpdateRequest req) {
        UsersEntity u = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        if (req.getRole() != null) validateRole(req.getRole());

        // Actualizar campos básicos
        if (req.getNames() != null) u.setNames(req.getNames());
        if (req.getSurnames() != null) u.setSurnames(req.getSurnames());
        if (req.getPersonalMail() != null) u.setPersonalMail(req.getPersonalMail());
        if (req.getStatement() != null) u.setStatement(req.getStatement());

        String newRole = req.getRole() != null ? req.getRole() : u.getRole();
        u.setRole(newRole);

        // Actualizar configuración (si viene)
        ConfigurationEntity conf = u.getConfigurations();
        if (req.getSmsChannel() != null) conf.setSmsChannel(req.getSmsChannel());
        if (req.getMailChannel() != null) conf.setMailChannel(req.getMailChannel());
        if (req.getWhatsappChannel() != null) conf.setWhatsappChannel(req.getWhatsappChannel());
        configurationRepository.save(conf);

        // Manejo UserCareer según rol
        Optional<UserCareerEntity> ucOpt = userCareerRepository.findByUser_Id(userId);

        if ("ADMIN".equals(newRole)) {
            // ADMIN no debe tener user_career
            if (ucOpt.isPresent()) {
                userCareerRepository.delete(ucOpt.get());
            }
        } else {
            // no-admin debe tener career asignada
            Integer careerId = req.getCareerId();

            if (ucOpt.isEmpty()) {
                if (careerId == null) throw new RuntimeException("careerId es obligatorio para rol " + newRole);
                CareerEntity career = careerRepository.findById(careerId)
                        .orElseThrow(() -> new RuntimeException("careerId no existe"));

                UserCareerEntity uc = new UserCareerEntity();
                uc.setUser(u);
                uc.setCareer(career);
                uc.setAssignedDate(LocalDateTime.now());
                uc.setActive(true);
                userCareerRepository.save(uc);
            } else {
                UserCareerEntity uc = ucOpt.get();
                if (careerId != null) {
                    CareerEntity career = careerRepository.findById(careerId)
                            .orElseThrow(() -> new RuntimeException("careerId no existe"));
                    uc.setCareer(career);
                }
                uc.setActive(true);
                userCareerRepository.save(uc);
            }
        }

        u = usersRepository.save(u);

        UserCareerEntity ucFinal = userCareerRepository.findByUser_Id(userId).orElse(null);
        return mapToUserResponse(u, ucFinal);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Integer userId) {
        UsersEntity u = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));
        UserCareerEntity uc = userCareerRepository.findByUser_Id(userId).orElse(null);
        return mapToUserResponse(u, uc);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> listUsers(Integer facultyId, Integer careerId, String role, Boolean active) {

        // Caso 1: Si filtras por faculty/career, lo más eficiente es ir por UserCareer
        if (facultyId != null || careerId != null) {
            List<UserCareerEntity> ucs;

            if (careerId != null) {
                ucs = userCareerRepository.findByCareer_Id(careerId);
            } else {
                ucs = userCareerRepository.findByCareer_Faculty_Id(facultyId);
            }

            return ucs.stream()
                    .map(uc -> mapToUserResponse(uc.getUser(), uc))
                    .filter(r -> role == null || role.equals(r.getRole()))
                    .filter(r -> active == null || active.equals(r.getStatement()))
                    .collect(Collectors.toList());
        }

        // Caso 2: Sin filtros por faculty/career, lista directo users
        List<UsersEntity> users = usersRepository.findAll();

        return users.stream()
                .filter(u -> role == null || role.equals(u.getRole()))
                .filter(u -> active == null || active.equals(u.getStatement()))
                .map(u -> mapToUserResponse(u, userCareerRepository.findByUser_Id(u.getId()).orElse(null)))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void changePassword(Integer userId, ChangePasswordRequest req) {
        UsersEntity u = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        CredentialsEntity cred = u.getCredentials();
        if (!req.getCurrentPassword().equals(cred.getPassword())) {
            throw new RuntimeException("Contraseña actual incorrecta");
        }

        cred.setPassword(req.getNewPassword());
        cred.setDateModification(LocalDate.now());
        credentialsRepository.save(cred);
    }

    // ----------------- helpers -----------------

    private void validateRole(String role) {
        if (role == null || !ROLES.contains(role)) {
            throw new RuntimeException("Rol inválido. Permitidos: " + ROLES);
        }
    }

    private UserResponse mapToUserResponse(UsersEntity u, UserCareerEntity uc) {
        UserResponse r = new UserResponse();
        r.setIdUser(u.getId());
        r.setNames(u.getNames());
        r.setSurnames(u.getSurnames());
        r.setCardId(u.getCardId());
        r.setInstitutionalEmail(u.getInstitutionalEmail());
        r.setPersonalMail(u.getPersonalMail());
        r.setRole(u.getRole());
        r.setStatement(u.getStatement());

        // config
        if (u.getConfigurations() != null) {
            r.setSmsChannel(u.getConfigurations().getSmsChannel());
            r.setMailChannel(u.getConfigurations().getMailChannel());
            r.setWhatsappChannel(u.getConfigurations().getWhatsappChannel());
        }

        // faculty/career para no-admin
        if (uc != null && uc.getCareer() != null) {
            r.setIdCareer(uc.getCareer().getId());
            r.setCareerName(uc.getCareer().getCareerName());
            if (uc.getCareer().getFaculty() != null) {
                r.setIdFaculty(uc.getCareer().getFaculty().getId());
                r.setFacultyName(uc.getCareer().getFaculty().getFacultyName());
            }
        }

        return r;
    }

}
