package TP_Final.devhire.Model.DTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DeveloperPasswordDTO {

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d).{6,}$",
            message = "The password must have at least 6 characters, one capital letter and one number"
    )
    private String password;
}
