package TP_Final.devhire.Model.Entities;

import TP_Final.devhire.Security.Entities.CredentialsEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "admins")
public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String charge;
    @OneToOne
    @JoinColumn(name = "user_id")
    private CredentialsEntity credentials;
}
