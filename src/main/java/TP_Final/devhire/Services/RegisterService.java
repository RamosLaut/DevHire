package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.CompanyAssembler;
import TP_Final.devhire.Assemblers.DeveloperAssembler;
import TP_Final.devhire.Model.DTOS.CompanyDTO;
import TP_Final.devhire.Model.DTOS.CompanyRegisterDTO;
import TP_Final.devhire.Model.DTOS.DeveloperDTO;
import TP_Final.devhire.Model.DTOS.DeveloperRegisterDTO;
import TP_Final.devhire.Model.Entities.CompanyEntity;
import TP_Final.devhire.Model.Entities.DeveloperEntity;
import TP_Final.devhire.Exceptions.AlreadyExistsException;
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

    public EntityModel<DeveloperDTO> devRegister(@Valid DeveloperRegisterDTO developerDTO)throws AlreadyExistsException {
        if(credentialRepository.findByEmail(developerDTO.getEmail()).isPresent()){
            throw new AlreadyExistsException("Email already exists");
        }
        if (developerDTO.getEmail()==null) {
            throw new RuntimeException("Email can´t be null");
        }
        if (developerDTO.getPassword()==null) {
            throw new RuntimeException("Password can´t be null");
        }
        DeveloperEntity developer = DeveloperEntity.builder()
                .name(developerDTO.getName())
                .lastName(developerDTO.getLastName())
                .dni(developerDTO.getDni())
                .location(developerDTO.getLocation())
                .enabled(true)
                .build();
        CredentialsEntity credentials = CredentialsEntity.builder()
                .email(developerDTO.getEmail())
                .password(passwordEncoder.encode(developerDTO.getPassword()))
                .developer(developer)
                .build();
        RoleEntity devRole = roleRepository.findByRole(Roles.ROLE_DEV)
                .orElseThrow(() -> new RuntimeException("Dev rol not found"));
        credentials.setRoles(Set.of(devRole));
        credentialRepository.save(credentials);
        developer.setCredentials(credentials);
        return developerAssembler.toModel(developerRepository.save(developer));

    }

    public EntityModel<CompanyDTO> companyRegister(CompanyRegisterDTO companyRegisterDTO) throws AlreadyExistsException{
        if(credentialRepository.findByEmail(companyRegisterDTO.getEmail()).isPresent()){
            throw new AlreadyExistsException("Email already exists");
        }
        if (companyRegisterDTO.getEmail()==null) {
            throw new RuntimeException("Email can´t be null");
        }
        if (companyRegisterDTO.getPassword()==null) {
            throw new RuntimeException("Password can´t be null");
        }
        if(companyRepository.findByName(companyRegisterDTO.getName()).isPresent()){
            throw new AlreadyExistsException("Company name already exists");
        }
        CompanyEntity company = CompanyEntity.builder()
                .name(companyRegisterDTO.getName())
                .location(companyRegisterDTO.getLocation())
                .description(companyRegisterDTO.getDescription())
                .enabled(true)
                .build();
        CredentialsEntity credentials = CredentialsEntity.builder()
                .email(companyRegisterDTO.getEmail())
                .password(passwordEncoder.encode(companyRegisterDTO.getPassword()))
                .company(company)
                .build();
        RoleEntity companyRole = roleRepository.findByRole(Roles.ROLE_COMPANY)
                .orElseThrow(() -> new RuntimeException("Company rol not found"));
        credentials.setRoles(Set.of(companyRole));
        credentialRepository.save(credentials);
        company.setCredentials(credentials);
        return companyAssembler.toModel(companyRepository.save(company));
    }
}
