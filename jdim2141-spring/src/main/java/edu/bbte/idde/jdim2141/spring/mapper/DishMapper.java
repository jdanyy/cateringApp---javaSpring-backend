package edu.bbte.idde.jdim2141.spring.mapper;

import edu.bbte.idde.jdim2141.spring.model.domain.Dish;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InDishDto;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InMenuDishDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutDishDto;
import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DishMapper {

    Dish convertMenuDishToDomain(InMenuDishDto inMenuDishDto);

    List<Dish> convertMenuDishesToDomains(List<InMenuDishDto> inMenuDishDtoList);

    Dish convertInToDomain(InDishDto inDishDto);

    OutDishDto convertDomainToOut(Dish dish);

    Collection<OutDishDto> convertDomainsToOuts(Collection<Dish> dishes);

    void convertToUpdate(InDishDto inDishDto, @MappingTarget Dish dish);

    InDishDto convertOutToIn(OutDishDto outDishDto);

    Collection<InDishDto> convertOutsToIns(Collection<OutDishDto> outDishDtos);
    
}
