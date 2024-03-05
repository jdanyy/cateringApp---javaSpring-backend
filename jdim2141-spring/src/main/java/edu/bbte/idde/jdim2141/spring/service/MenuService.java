package edu.bbte.idde.jdim2141.spring.service;

import edu.bbte.idde.jdim2141.spring.constants.MenuPermission;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InMenuDishDto;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InMenuDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutDishDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutMenuDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.PaginatedData;
import edu.bbte.idde.jdim2141.spring.model.dto.request.PaginationCriteria;
import edu.bbte.idde.jdim2141.spring.model.dto.request.SortCriteria;

public interface MenuService {

    OutMenuDto createMenu(InMenuDto inMenuDto, Long ownerId);

    OutMenuDto findMenuById(Long id);

    PaginatedData<OutMenuDto> findAllMenu(
        Long userId,
        MenuPermission permission,
        PaginationCriteria page,
        SortCriteria sort
    );

    PaginatedData<OutMenuDto> findMenuByMenuTitle(
        Long userId,
        MenuPermission permission,
        String menuTitle, PaginationCriteria page,
        SortCriteria sort
    );

    OutMenuDto updateMenuById(Long menuId, InMenuDto inMenuDto, Long ownerId);

    void deleteMenuById(Long menuId, Long ownerId);

    OutMenuDto addDishToMenu(Long menuId, InMenuDishDto inMenuDishDto, Long ownerId);

    void deleteDishFromMenu(Long menuId, Long dishId, Long ownerId);

    PaginatedData<OutDishDto> findDishesForMenu(Long menuId, PaginationCriteria page,
        SortCriteria sort);

}
