package TP_Final.devhire.DTOS;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRegisterDTO {
    @NotEmpty
    private String name;
    @NotEmpty
    private String lastName;
    @Email
    private String email;
    @NotEmpty
    private String dni;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
