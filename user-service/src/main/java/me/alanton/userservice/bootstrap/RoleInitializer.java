package me.alanton.userservice.bootstrap;

import lombok.RequiredArgsConstructor;
import me.alanton.userservice.dto.request.RoleRequest;
import me.alanton.userservice.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RoleInitializer implements CommandLineRunner {
    private final RoleService roleService;

    private final String[] defaultRoles = {
            "USER",
            "ADMIN"
    };

    @Override
    public void run(String... args) throws Exception {
        for (String roleName : defaultRoles) {

            if (!roleService.isExistByName(roleName)) {
                RoleRequest role = new RoleRequest(roleName);

                roleService.createRole(role);
            }
        }
    }
}