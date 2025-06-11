package TP_Final.devhire.DTOS;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyRegisterDTO {
    @NotNull
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "La contraseña debe tener mínimo 8 caracteres, almenos una letra mayúscula y un número")
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String location;
    @NotBlank
    private String description;
}
