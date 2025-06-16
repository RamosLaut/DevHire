package TP_Final.devhire.Model.DTOS;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PasswordChangeDTO {
    @NotBlank(message = "The email is required")
    @Email
    private String email;
    @NotBlank(message = "The actual password is required")
    private String actualPassword;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "La contraseña debe tener mínimo 8 caracteres, al menos una letra mayúscula y un número")
    private String newPassword;

}
