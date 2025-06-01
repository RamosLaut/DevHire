package TP_Final.devhire.Security.Dtos;

import TP_Final.devhire.DTOS.CompanyDTO;
import TP_Final.devhire.DTOS.DeveloperDTO;
import lombok.Builder;
import org.springframework.hateoas.EntityModel;

@Builder
public record AuthResponse(String token, EntityModel<DeveloperDTO> usuario, EntityModel<CompanyDTO> company) {

}
