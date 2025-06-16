package TP_Final.devhire.Security.Services;

import TP_Final.devhire.Assemblers.CompanyAssembler;
import TP_Final.devhire.Assemblers.DeveloperAssembler;
import TP_Final.devhire.Exceptions.NotFoundException;
import TP_Final.devhire.Exceptions.UnauthorizedException;
import TP_Final.devhire.Model.DTOS.AdminDTO;
import TP_Final.devhire.Model.DTOS.CompanyDTO;
import TP_Final.devhire.Model.DTOS.DeveloperDTO;
import TP_Final.devhire.Model.DTOS.PasswordChangeDTO;
import TP_Final.devhire.Model.Entities.AdminEntity;
import TP_Final.devhire.Model.Entities.CompanyEntity;
import TP_Final.devhire.Model.Entities.DeveloperEntity;
import TP_Final.devhire.Model.Mappers.AdminMapper;
import TP_Final.devhire.Repositories.AdminRepository;
import TP_Final.devhire.Repositories.CompanyRepository;
import TP_Final.devhire.Repositories.DeveloperRepository;
import TP_Final.devhire.Security.Dtos.AuthRequest;
import TP_Final.devhire.Security.Dtos.AuthResponse;
import TP_Final.devhire.Security.Entities.CredentialsEntity;
import TP_Final.devhire.Security.Repositories.CredentialsRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final CredentialsRepository credentialsRepository;
    private final AuthenticationManager authenticationManager;
    private final DeveloperRepository developerRepository;
    private final CompanyRepository companyRepository;
    private final AdminRepository adminRepository;
    private final DeveloperAssembler developerAssembler;
    private final CompanyAssembler companyAssembler;
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthService(CredentialsRepository credentialsRepository,
                       AuthenticationManager authenticationManager, DeveloperRepository developerRepository, CompanyRepository companyRepository, AdminRepository adminRepository, DeveloperAssembler developerAssembler, CompanyAssembler companyAssembler, AdminMapper adminMapper, PasswordEncoder passwordEncoder) {
        this.credentialsRepository = credentialsRepository;
        this.authenticationManager = authenticationManager;
        this.developerRepository = developerRepository;
        this.companyRepository = companyRepository;
        this.adminRepository = adminRepository;
        this.developerAssembler = developerAssembler;
        this.companyAssembler = companyAssembler;
        this.adminMapper = adminMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails authenticate(AuthRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.username(),
                        input.password()
                )
        );
        return credentialsRepository.findByEmail(input.username()).orElseThrow();
    }
    public AuthResponse getAuthResponse(String token, String email){
        Optional<DeveloperEntity> userOpt = developerRepository.findByCredentials_Email(email);
        Optional<CompanyEntity> companyOpt = companyRepository.findByCredentials_Email(email);
        Optional<AdminEntity> adminOpt = adminRepository.findByCredentials_Email(email);
        if (userOpt.isPresent()) {
            EntityModel<DeveloperDTO> dto = developerAssembler.toModel(userOpt.get());
            return AuthResponse.builder().developer(dto).token(token).build();
        } else if(companyOpt.isPresent()){
            EntityModel<CompanyDTO> dto = companyAssembler.toOwnCompanyModel(companyOpt.get());
            return AuthResponse.builder().company(dto).token(token).build();
        } else{
            AdminDTO adminDTO = adminMapper.convertToAdminDTO(adminOpt.get());
            return AuthResponse.builder().admin(adminDTO).token(token).build();
        }
    }

    public String updatePassword(PasswordChangeDTO dto)throws NotFoundException, UnauthorizedException {
        CredentialsEntity credential = credentialsRepository.findByEmail(dto.getEmail()).orElseThrow(()->new NotFoundException("Email not found"));
        if (!passwordEncoder.matches(dto.getActualPassword(), credential.getPassword())) {
            throw new UnauthorizedException("Current password is incorrect");
        }
        if(dto.getActualPassword().equals(dto.getNewPassword())){
            throw new UnauthorizedException("New password cannot be the same as the current one");
        }
        credential.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        credentialsRepository.save(credential);
        return "Password updated successfully";
    }
}
