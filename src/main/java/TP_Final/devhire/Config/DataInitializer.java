package TP_Final.devhire.Config;

import TP_Final.devhire.Security.Entities.RoleEntity;
import TP_Final.devhire.Security.Enums.Roles;
import TP_Final.devhire.Security.Repositories.RolesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Component
public class DataInitializer implements CommandLineRunner {
    private final RolesRepository roleRepository;

    public DataInitializer(RolesRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Override
    public void run(String... args) {
        Arrays.stream(Roles.values()).forEach(enumRol -> {
            roleRepository.findByRole(enumRol).orElseGet(() -> {
                RoleEntity nuevoRol = new RoleEntity(enumRol);
                return roleRepository.save(nuevoRol);
            });
        });
    }
}