package edu.bbte.idde.jdim2141.spring.mapper;

import edu.bbte.idde.jdim2141.spring.model.domain.Role;
import edu.bbte.idde.jdim2141.spring.model.domain.User;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InRole;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InUserLoginDto;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InUserRegisterDto;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InUserRoleDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutRoleDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutUserDto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-05T16:52:44+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.37.0.v20240206-1609, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User convertLoginInToDomain(InUserLoginDto inUserLoginDto) {
        if ( inUserLoginDto == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( inUserLoginDto.getEmail() );

        return user;
    }

    @Override
    public User convertRegisterInToDomain(InUserRegisterDto inUserRegisterDto) {
        if ( inUserRegisterDto == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( inUserRegisterDto.getEmail() );
        user.setLang( inUserRegisterDto.getLang() );
        user.setName( inUserRegisterDto.getName() );
        user.setTheme( inUserRegisterDto.getTheme() );

        return user;
    }

    @Override
    public OutUserDto convertDomainToOut(User user) {
        if ( user == null ) {
            return null;
        }

        OutUserDto outUserDto = new OutUserDto();

        outUserDto.setEmail( user.getEmail() );
        outUserDto.setId( user.getId() );
        outUserDto.setLang( user.getLang() );
        outUserDto.setName( user.getName() );
        outUserDto.setRoles( roleListToOutRoleDtoList( user.getRoles() ) );
        outUserDto.setTheme( user.getTheme() );

        return outUserDto;
    }

    @Override
    public Collection<OutUserDto> convertDomainsToOuts(Collection<User> users) {
        if ( users == null ) {
            return null;
        }

        Collection<OutUserDto> collection = new ArrayList<OutUserDto>( users.size() );
        for ( User user : users ) {
            collection.add( convertDomainToOut( user ) );
        }

        return collection;
    }

    @Override
    public void convertInUserRoleToDomain(InUserRoleDto inUserRoleDto, User user) {
        if ( inUserRoleDto == null ) {
            return;
        }

        if ( user.getRoles() != null ) {
            List<Role> list = inRoleListToRoleList( inUserRoleDto.getRoles() );
            if ( list != null ) {
                user.getRoles().clear();
                user.getRoles().addAll( list );
            }
            else {
                user.setRoles( null );
            }
        }
        else {
            List<Role> list = inRoleListToRoleList( inUserRoleDto.getRoles() );
            if ( list != null ) {
                user.setRoles( list );
            }
        }
    }

    protected List<OutRoleDto> roleListToOutRoleDtoList(List<Role> list) {
        if ( list == null ) {
            return null;
        }

        List<OutRoleDto> list1 = new ArrayList<OutRoleDto>( list.size() );
        for ( Role role : list ) {
            list1.add( roleMapper.convertDomainToOut( role ) );
        }

        return list1;
    }

    protected List<Role> inRoleListToRoleList(List<InRole> list) {
        if ( list == null ) {
            return null;
        }

        List<Role> list1 = new ArrayList<Role>( list.size() );
        for ( InRole inRole : list ) {
            list1.add( roleMapper.convertInToDomain( inRole ) );
        }

        return list1;
    }
}
