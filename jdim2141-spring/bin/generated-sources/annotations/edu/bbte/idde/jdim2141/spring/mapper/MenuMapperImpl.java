package edu.bbte.idde.jdim2141.spring.mapper;

import edu.bbte.idde.jdim2141.spring.model.domain.Dish;
import edu.bbte.idde.jdim2141.spring.model.domain.Menu;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InMenuDishDto;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InMenuDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutDishDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutMenuDto;
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
public class MenuMapperImpl implements MenuMapper {

    @Autowired
    private DishMapper dishMapper;

    @Override
    public Menu convertInToDomain(InMenuDto inMenuDto) {
        if ( inMenuDto == null ) {
            return null;
        }

        Menu menu = new Menu();

        menu.setCalories( inMenuDto.getCalories() );
        menu.setDishes( dishMapper.convertMenuDishesToDomains( inMenuDto.getDishes() ) );
        menu.setMenuTitle( inMenuDto.getMenuTitle() );
        menu.setPrice( inMenuDto.getPrice() );
        menu.setRating( inMenuDto.getRating() );

        return menu;
    }

    @Override
    public OutMenuDto convertDomainToOut(Menu menu) {
        if ( menu == null ) {
            return null;
        }

        OutMenuDto outMenuDto = new OutMenuDto();

        outMenuDto.setCalories( menu.getCalories() );
        outMenuDto.setDishes( dishListToOutDishDtoList( menu.getDishes() ) );
        outMenuDto.setId( menu.getId() );
        outMenuDto.setMenuTitle( menu.getMenuTitle() );
        outMenuDto.setPrice( menu.getPrice() );
        outMenuDto.setRating( menu.getRating() );

        return outMenuDto;
    }

    @Override
    public Collection<OutMenuDto> convertDomainsToOuts(Collection<Menu> menus) {
        if ( menus == null ) {
            return null;
        }

        Collection<OutMenuDto> collection = new ArrayList<OutMenuDto>( menus.size() );
        for ( Menu menu : menus ) {
            collection.add( convertDomainToOut( menu ) );
        }

        return collection;
    }

    @Override
    public void convertToUpdate(InMenuDto inMenuDto, Menu menu) {
        if ( inMenuDto == null ) {
            return;
        }

        menu.setCalories( inMenuDto.getCalories() );
        if ( menu.getDishes() != null ) {
            List<Dish> list = dishMapper.convertMenuDishesToDomains( inMenuDto.getDishes() );
            if ( list != null ) {
                menu.getDishes().clear();
                menu.getDishes().addAll( list );
            }
            else {
                menu.setDishes( null );
            }
        }
        else {
            List<Dish> list = dishMapper.convertMenuDishesToDomains( inMenuDto.getDishes() );
            if ( list != null ) {
                menu.setDishes( list );
            }
        }
        menu.setMenuTitle( inMenuDto.getMenuTitle() );
        menu.setPrice( inMenuDto.getPrice() );
        menu.setRating( inMenuDto.getRating() );
    }

    @Override
    public InMenuDto convertOutToIn(OutMenuDto outMenuDto) {
        if ( outMenuDto == null ) {
            return null;
        }

        InMenuDto inMenuDto = new InMenuDto();

        inMenuDto.setCalories( outMenuDto.getCalories() );
        inMenuDto.setDishes( outDishDtoListToInMenuDishDtoList( outMenuDto.getDishes() ) );
        inMenuDto.setMenuTitle( outMenuDto.getMenuTitle() );
        inMenuDto.setPrice( outMenuDto.getPrice() );
        inMenuDto.setRating( outMenuDto.getRating() );

        return inMenuDto;
    }

    protected List<OutDishDto> dishListToOutDishDtoList(List<Dish> list) {
        if ( list == null ) {
            return null;
        }

        List<OutDishDto> list1 = new ArrayList<OutDishDto>( list.size() );
        for ( Dish dish : list ) {
            list1.add( dishMapper.convertDomainToOut( dish ) );
        }

        return list1;
    }

    protected InMenuDishDto outDishDtoToInMenuDishDto(OutDishDto outDishDto) {
        if ( outDishDto == null ) {
            return null;
        }

        InMenuDishDto inMenuDishDto = new InMenuDishDto();

        inMenuDishDto.setId( outDishDto.getId() );

        return inMenuDishDto;
    }

    protected List<InMenuDishDto> outDishDtoListToInMenuDishDtoList(List<OutDishDto> list) {
        if ( list == null ) {
            return null;
        }

        List<InMenuDishDto> list1 = new ArrayList<InMenuDishDto>( list.size() );
        for ( OutDishDto outDishDto : list ) {
            list1.add( outDishDtoToInMenuDishDto( outDishDto ) );
        }

        return list1;
    }
}
