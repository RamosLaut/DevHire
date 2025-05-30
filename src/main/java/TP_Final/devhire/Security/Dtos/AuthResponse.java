package TP_Final.devhire.Security.Dtos;

import TP_Final.devhire.DTOS.CompanyDTO;
import TP_Final.devhire.DTOS.UserDTO;
import lombok.Builder;
import org.springframework.hateoas.EntityModel;

@Builder
public record AuthResponse(String token, EntityModel<UserDTO> usuario, EntityModel<CompanyDTO> company) {

}
