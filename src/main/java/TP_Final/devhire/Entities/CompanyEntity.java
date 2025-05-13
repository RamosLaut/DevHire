package TP_Final.devhire.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@Entity
@Table(name = "companies")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long company_id;

    @NotEmpty
    private String name;
    @NotNull
    private String location;

    private String description;
    @NotNull
    private Boolean state;

    @OneToMany(mappedBy = "company")
    private List<JobEntity> jobs;

}
