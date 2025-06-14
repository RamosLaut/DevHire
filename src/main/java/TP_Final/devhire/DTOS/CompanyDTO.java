package TP_Final.devhire.DTOS;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDTO {
    @NotNull
    Long id;
    @NotEmpty(message = "Company name cannot be empty")
    String name;
    @NotEmpty(message = "Company location cannot be empty")
    String location;
    @NotEmpty(message = "Company description cannot be empty")
    String description;
}
