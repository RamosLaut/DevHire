package TP_Final.devhire.Config;

import TP_Final.devhire.Exceptions.NotFoundException;
import TP_Final.devhire.Model.Entities.AdminEntity;
import TP_Final.devhire.Repositories.AdminRepository;
import TP_Final.devhire.Security.Entities.CredentialsEntity;
import TP_Final.devhire.Security.Entities.PermitEntity;
import TP_Final.devhire.Security.Entities.RoleEntity;
import TP_Final.devhire.Security.Enums.Permits;
import TP_Final.devhire.Security.Enums.Roles;
import TP_Final.devhire.Security.Repositories.CredentialsRepository;
import TP_Final.devhire.Security.Repositories.PermitRepository;
import TP_Final.devhire.Security.Repositories.RolesRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RolesRepository roleRepository;
    private final PermitRepository permitRepository;
    private final CredentialsRepository credentialsRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    public DataInitializer(RolesRepository roleRepository, PermitRepository permitRepository, CredentialsRepository credentialsRepository, AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.permitRepository = permitRepository;
        this.credentialsRepository = credentialsRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void run(String... args) {
        for (Permits p : Permits.values()) {
            permitRepository.findByPermit(p).orElseGet(() ->
                    permitRepository.save(new PermitEntity(null, p))
            );
        }

        createRoleWithPermits(Roles.ROLE_DEV, Set.of(
                Permits.READ_PROFILE, Permits.UPDATE_PROFILE,
                Permits.FILTER_DEVS, Permits.READ_DEVS,
                Permits.UPDATE_ACADEMIC_INFO, Permits.UPDATE_JOB_EXPERIENCE,
                Permits.UPDATE_SKILLS, Permits.LOGIC_DOWN,
                Permits.LOGIC_UP,

                Permits.READ_COMPANIES, Permits.FILTER_COMPANIES,

                Permits.CREATE_PUBLICATION, Permits.READ_PUBLICATIONS,
                Permits.FILTER_PUBLICATIONS, Permits.UPDATE_PUBLICATION,
                Permits.DELETE_PUBLICATION,

                Permits.CREATE_COMMENT, Permits.READ_COMMENTS,
                Permits.FILTER_COMMENTS, Permits.UPDATE_COMMENT,
                Permits.DELETE_COMMENT,

                Permits.LIKE_PUBLICATION, Permits.UNLIKE_PUBLICATION,
                Permits.FILTER_LIKES, Permits.READ_LIKES,

                Permits.READ_JOBS, Permits.FILTER_JOBS,
                Permits.READ_JOB_REQUIREMENTS, Permits.APPLY_TO_JOB,

                Permits.FOLLOW, Permits.UNFOLLOW,
                Permits.READ_FOLLOWERS, Permits.READ_FOLLOWING,
                Permits.FILTER_FOLLOWERS, Permits.FILTER_FOLLOWING,
                Permits.DEACTIVATE_FOLLOW, Permits.REACTIVATE_FOLLOW,

                Permits.DELETE_APPLY, Permits.READ_OWN_APPLICATIONS,
                Permits.FILTER_APPLICATIONS

        ));
        createRoleWithPermits(Roles.ROLE_COMPANY, Set.of(
                Permits.READ_PROFILE, Permits.UPDATE_PROFILE,
                Permits.READ_COMPANIES, Permits.FILTER_COMPANIES,
                Permits.LOGIC_DOWN,

                Permits.FILTER_DEVS, Permits.READ_DEVS,

                Permits.CREATE_PUBLICATION, Permits.READ_PUBLICATIONS,
                Permits.FILTER_PUBLICATIONS, Permits.UPDATE_PUBLICATION,
                Permits.DELETE_PUBLICATION,

                Permits.CREATE_COMMENT, Permits.READ_COMMENTS,
                Permits.FILTER_COMMENTS, Permits.UPDATE_COMMENT,
                Permits.DELETE_COMMENT,

                Permits.LIKE_PUBLICATION, Permits.UNLIKE_PUBLICATION,
                Permits.FILTER_LIKES, Permits.READ_LIKES,

                Permits.CREATE_JOB, Permits.UPDATE_JOB,
                Permits.READ_JOBS, Permits.FILTER_JOBS,
                Permits.DELETE_JOB, Permits.READ_JOB_REQUIREMENTS,
                Permits.READ_AVAILABLE_SKILLS, Permits.ADD_JOB_REQUIREMENT,

                Permits.FOLLOW, Permits.UNFOLLOW,
                Permits.READ_FOLLOWERS, Permits.READ_FOLLOWING,
                Permits.FILTER_FOLLOWERS, Permits.FILTER_FOLLOWING,

                Permits.READ_APPLICANTS, Permits.FILTER_APPLICANTS,
                Permits.ACCEPT_APPLICANT, Permits.REJECT_APPLICANT
        ));

        createRoleWithPermits(Roles.ROLE_ADMIN, Set.of(
                Permits.READ_PROFILE, Permits.UPDATE_PROFILE,
                Permits.READ_COMPANIES, Permits.FILTER_COMPANIES,
                Permits.LOGIC_DOWN, Permits.DELETE_ACCOUNT,

                Permits.FILTER_DEVS, Permits.READ_DEVS,

                Permits.READ_PUBLICATIONS, Permits.FILTER_PUBLICATIONS,
                Permits.DELETE_PUBLICATION,

                Permits.READ_COMMENTS, Permits.FILTER_COMMENTS,
                Permits.DELETE_COMMENT,

                Permits.FILTER_LIKES, Permits.READ_LIKES,

                Permits.READ_JOBS, Permits.FILTER_JOBS,
                Permits.DELETE_JOB,

                Permits.READ_FOLLOWERS, Permits.READ_FOLLOWING,
                Permits.FILTER_FOLLOWERS, Permits.FILTER_FOLLOWING,

                Permits.READ_APPLICATIONS,

                Permits.GET_STATS
        ));
        Dotenv dotenv = Dotenv.load();
        String email = dotenv.get("ADMIN_EMAIL");
        String password = dotenv.get("ADMIN_PASSWORD");
        AdminEntity admin = AdminEntity.builder().name("Lautaro").lastName("Ramos").charge("Backend Developer").build();
        CredentialsEntity credentials = CredentialsEntity.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .roles(Set.of(roleRepository.findByRole(Roles.ROLE_ADMIN).orElseThrow(()-> new NotFoundException("ROLE_ADMIN not found"))))
                .build();
        credentialsRepository.save(credentials);
        admin.setCredentials(credentials);
        adminRepository.save(admin);
    }
    private void createRoleWithPermits(Roles roleEnum, Set<Permits> permits) {
        RoleEntity role = roleRepository.findByRole(roleEnum)
                .orElseGet(() -> roleRepository.save(new RoleEntity(roleEnum)));

        for (Permits p : permits) {
            PermitEntity permit = permitRepository.findByPermit(p).orElseThrow();
            role.addPermit(permit);
        }

        roleRepository.save(role);
    }
}