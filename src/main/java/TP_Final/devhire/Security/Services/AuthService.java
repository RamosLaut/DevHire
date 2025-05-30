package TP_Final.devhire.Security.Services;

import TP_Final.devhire.Assemblers.CompanyAssembler;
import TP_Final.devhire.Assemblers.UserAssembler;
import TP_Final.devhire.DTOS.CompanyDTO;
import TP_Final.devhire.DTOS.UserDTO;
import TP_Final.devhire.Entities.CompanyEntity;
import TP_Final.devhire.Entities.UserEntity;
import TP_Final.devhire.Repositories.CompanyRepository;
import TP_Final.devhire.Repositories.UserRepository;
import TP_Final.devhire.Security.Dtos.AuthRequest;
import TP_Final.devhire.Security.Dtos.AuthResponse;
import TP_Final.devhire.Security.Repositories.CredentialsRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final CredentialsRepository credentialsRepository;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final UserAssembler userAssembler;
    private final CompanyAssembler companyAssembler;

    public AuthService(CredentialsRepository credentialsRepository,
                       AuthenticationManager authenticationManager, UserRepository userRepository, CompanyRepository companyRepository, UserAssembler userAssembler, CompanyAssembler companyAssembler) {
        this.credentialsRepository = credentialsRepository;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.userAssembler = userAssembler;
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
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);
        Optional<CompanyEntity> companyOpt = companyRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            EntityModel<UserDTO> dto = userAssembler.toModel(userOpt.get());
            return AuthResponse.builder().usuario(dto).token(token).build();
        } else{
            EntityModel<CompanyDTO> dto = companyAssembler.toModel(companyOpt.get());
            return AuthResponse.builder().company(dto).token(token).build();
        }
    }
}
