package edu.bbte.idde.jdim2141.spring.mapper;

import edu.bbte.idde.jdim2141.spring.model.domain.Dish;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InDishDto;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InMenuDishDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutDishDto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-05T16:52:44+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.37.0.v20240206-1609, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class DishMapperImpl implements DishMapper {

    @Override
    public Dish convertMenuDishToDomain(InMenuDishDto inMenuDishDto) {
        if ( inMenuDishDto == null ) {
            return null;
        }

        Dish dish = new Dish();

        dish.setId( inMenuDishDto.getId() );

        return dish;
    }

    @Override
    public List<Dish> convertMenuDishesToDomains(List<InMenuDishDto> inMenuDishDtoList) {
        if ( inMenuDishDtoList == null ) {
            return null;
        }

        List<Dish> list = new ArrayList<Dish>( inMenuDishDtoList.size() );
        for ( InMenuDishDto inMenuDishDto : inMenuDishDtoList ) {
            list.add( convertMenuDishToDomain( inMenuDishDto ) );
        }

        return list;
    }

    @Override
    public Dish convertInToDomain(InDishDto inDishDto) {
        if ( inDishDto == null ) {
            return null;
        }

        Dish dish = new Dish();

        dish.setName( inDishDto.getName() );

        return dish;
    }

    @Override
    public OutDishDto convertDomainToOut(Dish dish) {
        if ( dish == null ) {
            return null;
        }

        OutDishDto outDishDto = new OutDishDto();

        outDishDto.setId( dish.getId() );
        outDishDto.setName( dish.getName() );

        return outDishDto;
    }

    @Override
    public Collection<OutDishDto> convertDomainsToOuts(Collection<Dish> dishes) {
        if ( dishes == null ) {
            return null;
        }

        Collection<OutDishDto> collection = new ArrayList<OutDishDto>( dishes.size() );
        for ( Dish dish : dishes ) {
            collection.add( convertDomainToOut( dish ) );
        }

        return collection;
    }

    @Override
    public void convertToUpdate(InDishDto inDishDto, Dish dish) {
        if ( inDishDto == null ) {
            return;
        }

        dish.setName( inDishDto.getName() );
    }

    @Override
    public InDishDto convertOutToIn(OutDishDto outDishDto) {
        if ( outDishDto == null ) {
            return null;
        }

        InDishDto inDishDto = new InDishDto();

        inDishDto.setName( outDishDto.getName() );

        return inDishDto;
    }

    @Override
    public Collection<InDishDto> convertOutsToIns(Collection<OutDishDto> outDishDtos) {
        if ( outDishDtos == null ) {
            return null;
        }

        Collection<InDishDto> collection = new ArrayList<InDishDto>( outDishDtos.size() );
        for ( OutDishDto outDishDto : outDishDtos ) {
            collection.add( convertOutToIn( outDishDto ) );
        }

        return collection;
    }
}
