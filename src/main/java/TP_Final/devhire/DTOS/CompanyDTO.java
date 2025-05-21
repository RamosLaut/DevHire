package TP_Final.devhire.DTOS;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CompanyDTO {
    Long company_id;
    String name;
    String location;
    String descriptiion;
    Boolean state;
}
