package TP_Final.devhire.Security.Entities;

import TP_Final.devhire.Model.Entities.AdminEntity;
import TP_Final.devhire.Model.Entities.CompanyEntity;
import TP_Final.devhire.Model.Entities.DeveloperEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "credentials")
public class CredentialsEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    @NotBlank
    private String password;

    @OneToOne(mappedBy = "credentials", cascade = CascadeType.ALL)
    @JoinColumn(name = "dev_id", referencedColumnName = "id", unique = true)
    private DeveloperEntity developer;

    @OneToOne(mappedBy = "credentials", cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id", unique = true)
    private CompanyEntity company;

    @OneToOne(mappedBy = "credentials", cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id", referencedColumnName = "id", unique = true)
    private AdminEntity admin;

    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(
            name = "credentials_roles",
            joinColumns = @JoinColumn(name = "credential_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        for (RoleEntity role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole().name().replace("ROLE_", "")));

            for (PermitEntity permit : role.getPermits()) {
                authorities.add(new SimpleGrantedAuthority(permit.getPermit().name()));
            }
        }
        return authorities;
    }
    @Override
    public String getUsername() {
        return this.email;
    }
    @Override
    public String getPassword() {
        return this.password;
    }
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

}
