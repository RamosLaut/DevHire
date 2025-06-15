package TP_Final.devhire.Security.Dtos;

import TP_Final.devhire.Model.DTOS.CompanyDTO;
import TP_Final.devhire.Model.DTOS.DeveloperDTO;
import lombok.Builder;
import org.springframework.hateoas.EntityModel;

@Builder
public record AuthResponse(String token, EntityModel<DeveloperDTO> developer, EntityModel<CompanyDTO> company) {

}
