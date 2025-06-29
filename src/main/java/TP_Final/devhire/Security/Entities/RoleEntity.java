package TP_Final.devhire.Security.Entities;

import TP_Final.devhire.Security.Enums.Roles;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Roles role;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permits",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permit_id"))
    private final Set<PermitEntity> permits = new HashSet<>();

    public RoleEntity(Roles name) {
        this.role = name;
    }

    public void addPermit(PermitEntity permit) {
        this.permits.add(permit);
    }

}
