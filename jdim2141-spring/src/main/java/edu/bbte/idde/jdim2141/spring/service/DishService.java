package edu.bbte.idde.jdim2141.spring.service;

import edu.bbte.idde.jdim2141.spring.model.domain.Dish;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InDishDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutDishDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.PaginatedData;
import edu.bbte.idde.jdim2141.spring.model.dto.request.PaginationCriteria;
import edu.bbte.idde.jdim2141.spring.model.dto.request.SortCriteria;
import java.util.List;

public interface DishService {

    OutDishDto createDish(InDishDto inDishDto);

    OutDishDto findDishById(Long id);

    Dish findDishByIdDomain(Long id);

    PaginatedData<OutDishDto> findDishesPageFromAMenu(List<Long> ids, PaginationCriteria page,
        SortCriteria sort, Long menuId);

    PaginatedData<OutDishDto> findAllDishes(PaginationCriteria page, SortCriteria sort);

    PaginatedData<OutDishDto> findDishesByName(String name, PaginationCriteria page,
        SortCriteria sort);

    OutDishDto updateDishById(Long id, InDishDto inDishDto);

    void deleteDishById(Long id);

}
