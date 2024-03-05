package edu.bbte.idde.jdim2141.spring.repository;

import edu.bbte.idde.jdim2141.spring.constants.UserRole;
import edu.bbte.idde.jdim2141.spring.model.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByName(UserRole userRole);
}
