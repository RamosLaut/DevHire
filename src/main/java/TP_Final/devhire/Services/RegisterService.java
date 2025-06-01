package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.CompanyAssembler;
import TP_Final.devhire.Assemblers.DeveloperAssembler;
import TP_Final.devhire.DTOS.*;
import TP_Final.devhire.Entities.CompanyEntity;
import TP_Final.devhire.Entities.DeveloperEntity;
import TP_Final.devhire.Exceptions.ExistingEmailException;
import TP_Final.devhire.Repositories.CompanyRepository;
import TP_Final.devhire.Repositories.DeveloperRepository;
import TP_Final.devhire.Security.Entities.CredentialsEntity;
import TP_Final.devhire.Security.Entities.RoleEntity;
import TP_Final.devhire.Security.Enums.Roles;
import TP_Final.devhire.Security.Repositories.CredentialsRepository;
import TP_Final.devhire.Security.Repositories.RolesRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Service
public class RegisterService {
    private final CredentialsRepository credentialRepository;
    private final DeveloperAssembler developerAssembler;
    private final CompanyAssembler companyAssembler;
    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;
    private final RolesRepository roleRepository;
    private final DeveloperRepository developerRepository;
    @Autowired
    public RegisterService(CredentialsRepository credentialRepository, DeveloperAssembler developerAssembler, CompanyAssembler companyAssembler, PasswordEncoder passwordEncoder, CompanyRepository companyRepository, RolesRepository roleRepository, DeveloperRepository developerRepository) {
        this.credentialRepository = credentialRepository;
        this.developerAssembler = developerAssembler;
        this.companyAssembler = companyAssembler;
        this.passwordEncoder = passwordEncoder;
        this.companyRepository = companyRepository;
        this.roleRepository = roleRepository;
        this.developerRepository = developerRepository;
    }

    public EntityModel<DeveloperDTO> devRegister(@Valid DeveloperRegisterDTO developerDTO) {
        if(credentialRepository.findByEmail(developerDTO.getEmail()).isPresent()){
            throw new ExistingEmailException("Email already exists");
        } else if (developerDTO.getEmail()==null) {
            throw new RuntimeException("Email can´t be null");
        } else if (developerDTO.getPassword()==null) {
            throw new RuntimeException("Password can´t be null");
        }
        DeveloperEntity developer = DeveloperEntity.builder()
                .name(developerDTO.getName())
                .lastName(developerDTO.getLastName())
                .dni(developerDTO.getDni())
                .location(developerDTO.getLocation())
                .state(true)
                .build();
        CredentialsEntity credentials = CredentialsEntity.builder()
                .email(developerDTO.getEmail())
                .password(passwordEncoder.encode(developerDTO.getPassword()))
                .developer(developer)
                .build();
        RoleEntity devRole = roleRepository.findByRole(Roles.ROLE_DEV)
                .orElseThrow(() -> new RuntimeException("Dev rol not found"));
        credentials.setRoles(Set.of(devRole));
        developer.setCredentials(credentials);
        credentialRepository.save(credentials);
        return developerAssembler.toModel(developerRepository.save(developer));

    }

    public EntityModel<CompanyDTO> companyRegister(CompanyRegisterDTO companyRegisterDTO) {
        if(credentialRepository.findByEmail(companyRegisterDTO.getEmail()).isPresent()){
            throw new ExistingEmailException("Email already exists");
        } else if (companyRegisterDTO.getEmail()==null) {
            throw new RuntimeException("Email can´t be null");
        } else if (companyRegisterDTO.getPassword()==null) {
            throw new RuntimeException("Password can´t be null");
        }
        CompanyEntity company = CompanyEntity.builder()
                .name(companyRegisterDTO.getName())
                .location(companyRegisterDTO.getLocation())
                .description(companyRegisterDTO.getDescription())
                .state(true)
                .build();
        CredentialsEntity credentials = CredentialsEntity.builder()
                .email(companyRegisterDTO.getEmail())
                .password(passwordEncoder.encode(companyRegisterDTO.getPassword()))
                .company(company)
                .build();
        RoleEntity companyRole = roleRepository.findByRole(Roles.ROLE_COMPANY)
                .orElseThrow(() -> new RuntimeException("Company rol not found"));
        credentials.setRoles(Set.of(companyRole));
        company.setCredentials(credentials);
        credentialRepository.save(credentials);
        return companyAssembler.toModel(companyRepository.save(company));
    }
}
