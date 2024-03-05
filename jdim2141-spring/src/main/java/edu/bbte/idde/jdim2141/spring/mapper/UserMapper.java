package edu.bbte.idde.jdim2141.spring.mapper;

import edu.bbte.idde.jdim2141.spring.model.domain.User;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InUserLoginDto;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InUserRegisterDto;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InUserRoleDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutUserDto;
import java.util.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {

    User convertLoginInToDomain(InUserLoginDto inUserLoginDto);

    User convertRegisterInToDomain(InUserRegisterDto inUserRegisterDto);

    OutUserDto convertDomainToOut(User user);

    Collection<OutUserDto> convertDomainsToOuts(Collection<User> users);

    void convertInUserRoleToDomain(InUserRoleDto inUserRoleDto, @MappingTarget User user);
}
