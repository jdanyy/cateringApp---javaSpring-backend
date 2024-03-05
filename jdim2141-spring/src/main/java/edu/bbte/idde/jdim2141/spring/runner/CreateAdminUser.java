package edu.bbte.idde.jdim2141.spring.runner;

import edu.bbte.idde.jdim2141.spring.constants.UserRole;
import edu.bbte.idde.jdim2141.spring.exception.service.ServiceEntityConflictException;
import edu.bbte.idde.jdim2141.spring.exception.service.UnexpectedServiceException;
import edu.bbte.idde.jdim2141.spring.model.domain.User;
import edu.bbte.idde.jdim2141.spring.repository.RoleRepository;
import edu.bbte.idde.jdim2141.spring.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateAdminUser implements ApplicationRunner {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) {
        try {
            var user = new User();
            user.setEmail("admin@mail.com");
            user.setName("Admin");
            user.setPasswordHash(
                "$2a$10$/vcC87lZB.2W39s8s3Ki7.nUS.lH4LFMWkHzIdzs1rBAPxYaT.Any"
            ); //admin

            var adminRole = roleRepository.findRoleByName(UserRole.ADMIN);
            user.setRoles(List.of(adminRole));

            var savedAdmin = userService.createUser(user);
            log.info("Admin user created: {}", savedAdmin);
        } catch (UnexpectedServiceException e) {
            log.error("Admin creation failed");
        } catch (ServiceEntityConflictException e) {
            log.debug("Cant create Admin, because already exists");
        }
    }
}
