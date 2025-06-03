package TP_Final.devhire.Security.Controllers;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            summary = "Autenticación de usuario",
            description = "Permite tanto al programador como a la empresa autenticarse en la aplicación.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credenciales para autenticarse",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = """
                {
                  "username": "usuario@example.com",
                  "password": "Password1"
                }
                """,
                                    implementation = AuthRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Autenticación exitosa, se devuelve el token y datos del usuario",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
                    @ApiResponse(responseCode = "400", description = "Formato incorrecto de la solicitud")
            }
    )
    @PostMapping
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest authRequest){
        UserDetails user = authService.authenticate(authRequest);
        String token = jwtService.generateToken(user);
        String email = user.getUsername();
        return ResponseEntity.ok(authService.getAuthResponse(token, email));
    }
}
