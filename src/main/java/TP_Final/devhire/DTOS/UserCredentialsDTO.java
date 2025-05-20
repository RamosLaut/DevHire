package TP_Final.devhire.DTOS;

import jakarta.validation.constraints.Pattern;

public class UserCredentialsDTO {
//    private Long user_id;
//    private String username;
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d).{6,}$",
            message = "The password must have at least 6 characters, one capital letter and one number"
    )
    private String password;
}
