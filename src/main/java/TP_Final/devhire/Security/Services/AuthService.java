package TP_Final.devhire.Security.Services;

import TP_Final.devhire.Assemblers.CompanyAssembler;
import TP_Final.devhire.Assemblers.DeveloperAssembler;
import TP_Final.devhire.Model.DTOS.CompanyDTO;
import TP_Final.devhire.Model.DTOS.DeveloperDTO;
import TP_Final.devhire.Model.Entities.CompanyEntity;
import TP_Final.devhire.Model.Entities.DeveloperEntity;
import TP_Final.devhire.Repositories.CompanyRepository;
import TP_Final.devhire.Repositories.DeveloperRepository;
import TP_Final.devhire.Security.Dtos.AuthRequest;
import TP_Final.devhire.Security.Dtos.AuthResponse;
import TP_Final.devhire.Security.Repositories.CredentialsRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final CredentialsRepository credentialsRepository;
    private final AuthenticationManager authenticationManager;
    private final DeveloperRepository developerRepository;
    private final CompanyRepository companyRepository;
    private final DeveloperAssembler developerAssembler;
    private final CompanyAssembler companyAssembler;

    public AuthService(CredentialsRepository credentialsRepository,
                       AuthenticationManager authenticationManager, DeveloperRepository developerRepository, CompanyRepository companyRepository, DeveloperAssembler developerAssembler, CompanyAssembler companyAssembler) {
        this.credentialsRepository = credentialsRepository;
        this.authenticationManager = authenticationManager;
        this.developerRepository = developerRepository;
        this.companyRepository = companyRepository;
        this.developerAssembler = developerAssembler;
        this.companyAssembler = companyAssembler;
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

        if (userOpt.isPresent()) {
            EntityModel<DeveloperDTO> dto = developerAssembler.toModel(userOpt.get());
            return AuthResponse.builder().developer(dto).token(token).build();
        } else{
            EntityModel<CompanyDTO> dto = companyAssembler.toModel(companyOpt.get());
            return AuthResponse.builder().company(dto).token(token).build();
        }
    }
}
