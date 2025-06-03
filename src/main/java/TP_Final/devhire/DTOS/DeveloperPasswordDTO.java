package TP_Final.devhire.DTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DeveloperPasswordDTO {
//    private Long user_id;
//    private String username;
    @NotBlank
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d).{6,}$",
            message = "The password must have at least 6 characters, one capital letter and one number"
    )
    private String password;
}
