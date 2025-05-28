package TP_Final.devhire.Security.Services;

import TP_Final.devhire.Security.DTO.AuthRequest;
import TP_Final.devhire.Security.Repositorys.CredentialsRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthService {
    private  final CredentialsRepository credentialsRepository;
    private final AuthenticationManager authenticationManager;

    public AuthService(CredentialsRepository credentialsRepository, AuthenticationManager authenticationManager) {
        this.credentialsRepository = credentialsRepository;
        this.authenticationManager = authenticationManager;
    }

    //Utiliza la clase AuthenticationManager para autenticar el usuario.
    public UserDetails authenticate(AuthRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.username(),
                        input.password()
                )
        );
        return credentialsRepository.findByEmail(input.username()).orElseThrow();
    }
}
