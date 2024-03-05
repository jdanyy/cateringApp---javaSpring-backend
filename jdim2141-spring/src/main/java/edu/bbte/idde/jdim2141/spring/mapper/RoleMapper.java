package edu.bbte.idde.jdim2141.spring.mapper;

import edu.bbte.idde.jdim2141.spring.model.domain.Role;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InRole;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutRoleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role convertInToDomain(InRole inRole);

    OutRoleDto convertDomainToOut(Role role);
}
