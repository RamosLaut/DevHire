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
    @NotEmpty
    String name;
    @NotEmpty
    String location;
    @NotEmpty
    String description;
}
