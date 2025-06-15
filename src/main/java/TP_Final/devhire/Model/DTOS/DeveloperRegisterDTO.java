package TP_Final.devhire.Model.DTOS;

import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class DeveloperRegisterDTO {
    @NotNull
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "La contraseña debe tener mínimo 8 caracteres, al menos una letra mayúscula y un número")
    private String password;
    @NotBlank
    private String name;
    @NotEmpty
    private String lastName;
    @NotBlank
    private String dni;
    @NotBlank
    private String location;
}
