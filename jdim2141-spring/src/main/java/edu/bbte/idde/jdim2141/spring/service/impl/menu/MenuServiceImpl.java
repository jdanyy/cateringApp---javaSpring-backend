package edu.bbte.idde.jdim2141.spring.service.impl.menu;

import edu.bbte.idde.jdim2141.spring.constants.MenuPermission;
import edu.bbte.idde.jdim2141.spring.exception.repository.UnexpectedRepositoryException;
import edu.bbte.idde.jdim2141.spring.exception.service.ServiceEntityNotFoundException;
import edu.bbte.idde.jdim2141.spring.exception.service.UnexpectedServiceException;
import edu.bbte.idde.jdim2141.spring.mapper.MenuMapper;
import edu.bbte.idde.jdim2141.spring.model.domain.BaseEntity;
import edu.bbte.idde.jdim2141.spring.model.domain.Dish;
import edu.bbte.idde.jdim2141.spring.model.domain.Menu;
import edu.bbte.idde.jdim2141.spring.model.domain.User;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InMenuDishDto;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InMenuDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutDishDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutMenuDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.PaginatedData;
import edu.bbte.idde.jdim2141.spring.model.dto.request.PaginationCriteria;
import edu.bbte.idde.jdim2141.spring.model.dto.request.SortCriteria;
import edu.bbte.idde.jdim2141.spring.repository.MenuRepository;
import edu.bbte.idde.jdim2141.spring.service.DishService;
import edu.bbte.idde.jdim2141.spring.service.MenuService;
import edu.bbte.idde.jdim2141.spring.service.UserService;
import edu.bbte.idde.jdim2141.spring.util.PaginationUtil;
import edu.bbte.idde.jdim2141.spring.util.SortingUtil;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final UserService userService;
    private final DishService dishService;
    private final MenuMapper menuMapper;

    @Override
    @Transactional
    public OutMenuDto createMenu(InMenuDto inMenuDto, Long chefId) {
        Menu menu = menuMapper.convertInToDomain(inMenuDto);
        log.info("{}", menu);

        var dishIds = menu.getDishes().stream().map(BaseEntity::getId).toList();
        var dishes = fetchDishesForMenu(dishIds);
        menu.setDishes(dishes);

        User chef = userService.getDomainUserById(chefId);
        menu.setChef(chef);

        Menu savedMenu;
        try {
            savedMenu = menuRepository.save(menu);
        } catch (DataAccessException e) {
            log.error("Repository exception: {}", e.getMessage());
            throw new UnexpectedServiceException("Create repository error", e);
        }

        OutMenuDto outMenuDto = menuMapper.convertDomainToOut(savedMenu);
        log.debug("Create menu response: {}", outMenuDto);
        return outMenuDto;
    }

    private List<Dish> fetchDishesForMenu(List<Long> dishIds) {
        return dishIds.stream().map(dishService::findDishByIdDomain).toList();
    }

    @Override
    public OutMenuDto findMenuById(Long id) {
        Optional<Menu> optionalMenu;
        try {
            optionalMenu = menuRepository.findById(id);
        } catch (DataAccessException e) {
            log.error("Repository exception: {}", e.getMessage());
            throw new UnexpectedServiceException("Find repository error", e);
        }

        if (optionalMenu.isEmpty()) {
            throw new ServiceEntityNotFoundException("Menu entity not found");
        }

        OutMenuDto outMenuDto = menuMapper.convertDomainToOut(optionalMenu.get());
        log.debug("FindById response: {}", outMenuDto);
        return outMenuDto;
    }

    @Override
    public PaginatedData<OutMenuDto> findAllMenu(Long chefId, MenuPermission permission,
        PaginationCriteria page,
        SortCriteria sort) {
        SortingUtil.validateSortCriteria(sort, Menu.class);

        Pageable pageable = PageRequest.of(page.getPage(), page.getLimit(), sort.getOrder(),
            sort.getSortBy());

        Page<Menu> menusPage;
        switch (permission) {
            case OWNER -> menusPage = getAllMenusForChef(chefId, pageable);
            case CONSUMER -> menusPage = getAllMenusForConsumer(pageable);
            default -> throw new UnexpectedServiceException("Invalid menu Permission parameter");
        }

        var pagination = PaginationUtil.extractPageData(menusPage, "/api/menus", sort);

        var menus = menusPage.getContent();
        var outMenuList = menuMapper.convertDomainsToOuts(menus);

        return new PaginatedData<OutMenuDto>(outMenuList, pagination);
    }

    private Page<Menu> getAllMenusForChef(Long chefId, Pageable pageable) {
        try {
            return menuRepository.findAllByChef_Id(chefId, pageable);
        } catch (DataAccessException e) {
            throw new UnexpectedServiceException("Find all exception", e);
        }
    }

    private Page<Menu> getAllMenusForConsumer(Pageable pageable) {
        try {
            return menuRepository.findAll(pageable);
        } catch (DataAccessException e) {
            throw new UnexpectedServiceException("Find all exception", e);
        }
    }

    @Override
    public PaginatedData<OutMenuDto> findMenuByMenuTitle(
        Long userId,
        MenuPermission permission,
        String menuTitle,
        PaginationCriteria page,
        SortCriteria sort
    ) {
        SortingUtil.validateSortCriteria(sort, Menu.class);

        Pageable pageable = PageRequest.of(page.getPage(), page.getLimit(), sort.getOrder(),
            sort.getSortBy());

        Page<Menu> menusPage;

        switch (permission) {
            case OWNER -> menusPage = getMenusByTitleForOwner(userId, menuTitle, pageable);
            case CONSUMER -> menusPage = getMenusByTitleForConsumer(menuTitle, pageable);
            default -> throw new UnexpectedServiceException("Invalid Menu permission");
        }

        var pagination = PaginationUtil.extractPageData(menusPage,
            "/api/menus?menuTitle=" + menuTitle, sort);

        var menus = menusPage.getContent();
        var outMenuList = menuMapper.convertDomainsToOuts(menus);

        return new PaginatedData<OutMenuDto>(outMenuList, pagination);
    }

    private Page<Menu> getMenusByTitleForOwner(Long userId, String menuTitle, Pageable pageable) {
        try {
            return menuRepository.findAllByChef_IdAndMenuTitleContains(userId, menuTitle, pageable);
        } catch (DataAccessException e) {
            throw new UnexpectedServiceException("Find menu by MenuTitle and chef Id", e);
        }
    }

    private Page<Menu> getMenusByTitleForConsumer(String menuTitle, Pageable pageable) {
        try {
            return menuRepository.findAllByMenuTitleContains(menuTitle, pageable);
        } catch (DataAccessException e) {
            log.error("Repository exception: {}", e.getMessage());
            throw new UnexpectedServiceException("Find menu by menuTitle", e);
        }
    }

    @Override
    @Transactional
    public OutMenuDto updateMenuById(Long menuId, InMenuDto inMenuDto, Long chefId) {
        Optional<Menu> optionalMenu;
        try {
            optionalMenu = menuRepository.findByIdAndChef_Id(menuId, chefId);
        } catch (DataAccessException e) {
            throw new UnexpectedRepositoryException("Find by id error", e);
        }

        if (optionalMenu.isEmpty()) {
            throw new ServiceEntityNotFoundException("Menu entity not found");
        }

        Menu updateMenu = optionalMenu.get();
        menuMapper.convertToUpdate(inMenuDto, updateMenu);

        var dishIds = updateMenu.getDishes().stream().map(BaseEntity::getId).toList();
        var dishes = fetchDishesForMenu(dishIds);
        updateMenu.setDishes(new ArrayList<>(dishes));

        try {
            menuRepository.save(updateMenu);
        } catch (DataAccessException e) {
            log.error("Repository exception: {}", e.getMessage());
            throw new UnexpectedServiceException("Update menu by Id error", e);
        }

        return menuMapper.convertDomainToOut(updateMenu);
    }

    @Override
    public void deleteMenuById(Long menuId, Long chefId) {
        Optional<Menu> optionalMenu;
        try {
            optionalMenu = menuRepository.findByIdAndChef_Id(menuId, chefId);
        } catch (DataAccessException e) {
            log.error("Find by id erro on delete", e);
            throw new UnexpectedServiceException(e);
        }

        if (optionalMenu.isEmpty()) {
            throw new ServiceEntityNotFoundException("Menu Entity not found");
        }

        try {
            menuRepository.deleteById(menuId);
        } catch (DataAccessException e) {
            log.error("Delete error", e);
            throw new UnexpectedServiceException(e);
        }
    }

    @Override
    public OutMenuDto addDishToMenu(Long menuId, InMenuDishDto inMenuDishDto, Long chefId) {
        Menu menu;
        try {
            menu = menuRepository.findByIdAndChef_Id(menuId, chefId)
                .orElseThrow(() -> new ServiceEntityNotFoundException("Menu not found"));
        } catch (DataAccessException e) {
            throw new UnexpectedServiceException(e);
        }

        Long dishId = inMenuDishDto.getId();
        Dish dish = dishService.findDishByIdDomain(dishId);

        var menuDishes = menu.getDishes();
        menuDishes.add(dish);
        menu.setDishes(new ArrayList<>(menuDishes));

        Menu updatedMenu;
        try {
            updatedMenu = menuRepository.save(menu);
        } catch (DataAccessException e) {
            throw new UnexpectedServiceException(e);
        }

        return menuMapper.convertDomainToOut(updatedMenu);
    }

    @Override
    public void deleteDishFromMenu(Long menuId, Long dishId, Long chefId) {
        Menu menu;
        try {
            menu = menuRepository.findByIdAndChef_Id(menuId, chefId)
                .orElseThrow(() -> new ServiceEntityNotFoundException("Menu not found"));
        } catch (DataAccessException e) {
            throw new UnexpectedServiceException(e);
        }

        dishService.findDishByIdDomain(dishId);

        var dishesForMenu = menu
            .getDishes()
            .stream()
            .filter(menuDish -> !menuDish.getId().equals(dishId))
            .toList();

        menu.setDishes(new ArrayList<>(dishesForMenu));
        try {
            menuRepository.save(menu);
        } catch (DataAccessException e) {
            throw new UnexpectedServiceException(e);
        }
    }

    @Override
    public PaginatedData<OutDishDto> findDishesForMenu(Long menuId, PaginationCriteria page,
        SortCriteria sort) {
        Menu menu = menuRepository.findById(menuId)
            .orElseThrow(
                () -> new ServiceEntityNotFoundException("Menu with id=" + menuId + " not found"));

        var dishId = menu.getDishes().stream().map(BaseEntity::getId).toList();

        return dishService.findDishesPageFromAMenu(dishId, page, sort, menuId);
    }

}

