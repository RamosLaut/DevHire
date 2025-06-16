package TP_Final.devhire.Security.Controllers;

import TP_Final.devhire.Exceptions.NotFoundException;
import TP_Final.devhire.Exceptions.UnauthorizedException;
import TP_Final.devhire.Model.DTOS.PasswordChangeDTO;
import TP_Final.devhire.Security.Dtos.AuthRequest;
import TP_Final.devhire.Security.Dtos.AuthResponse;
import TP_Final.devhire.Security.Services.AuthService;
import TP_Final.devhire.Security.Services.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;


    public AuthController(AuthService authService, JwtService
            jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @Operation(
            summary = "Autenticar usuario",
            description = "Recibe credenciales y retorna un token JWT junto con los datos del usuario o empresa autenticado.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Autenticación exitosa",
                            content = @Content(schema = @Schema(implementation = AuthResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Credenciales inválidas"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest authRequest){
        UserDetails user = authService.authenticate(authRequest);
        String token = jwtService.generateToken(user);
        String email = user.getUsername();
        return ResponseEntity.ok(authService.getAuthResponse(token, email));
    }
    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDTO dto)throws NotFoundException, UnauthorizedException{
        return ResponseEntity.ok(authService.updatePassword(dto));
    }
}
