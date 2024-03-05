package edu.bbte.idde.jdim2141.spring.mapper;

import edu.bbte.idde.jdim2141.spring.model.domain.Menu;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InMenuDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutMenuDto;
import java.util.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {DishMapper.class})
public interface MenuMapper {

    Menu convertInToDomain(InMenuDto inMenuDto);

    OutMenuDto convertDomainToOut(Menu menu);

    Collection<OutMenuDto> convertDomainsToOuts(Collection<Menu> menus);

    void convertToUpdate(InMenuDto inMenuDto, @MappingTarget Menu menu);

    InMenuDto convertOutToIn(OutMenuDto outMenuDto);

}
