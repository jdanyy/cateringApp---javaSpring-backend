package edu.bbte.idde.jdim2141.spring.mapper;

import edu.bbte.idde.jdim2141.spring.model.domain.Role;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InRole;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutRoleDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-05T16:52:44+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.37.0.v20240206-1609, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role convertInToDomain(InRole inRole) {
        if ( inRole == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( inRole.getId() );
        role.setName( inRole.getName() );

        return role;
    }

    @Override
    public OutRoleDto convertDomainToOut(Role role) {
        if ( role == null ) {
            return null;
        }

        OutRoleDto outRoleDto = new OutRoleDto();

        outRoleDto.setId( role.getId() );
        outRoleDto.setName( role.getName() );

        return outRoleDto;
    }
}
