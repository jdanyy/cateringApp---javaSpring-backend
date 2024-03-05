package edu.bbte.idde.jdim2141.spring.service.impl.dish;

import edu.bbte.idde.jdim2141.spring.exception.repository.UnexpectedRepositoryException;
import edu.bbte.idde.jdim2141.spring.exception.service.ServiceEntityNotFoundException;
import edu.bbte.idde.jdim2141.spring.exception.service.UnexpectedServiceException;
import edu.bbte.idde.jdim2141.spring.mapper.DishMapper;
import edu.bbte.idde.jdim2141.spring.model.domain.Dish;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InDishDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutDishDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.PaginatedData;
import edu.bbte.idde.jdim2141.spring.model.dto.request.PaginationCriteria;
import edu.bbte.idde.jdim2141.spring.model.dto.request.SortCriteria;
import edu.bbte.idde.jdim2141.spring.repository.DishRepository;
import edu.bbte.idde.jdim2141.spring.service.DishService;
import edu.bbte.idde.jdim2141.spring.util.PaginationUtil;
import edu.bbte.idde.jdim2141.spring.util.SortingUtil;
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
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    @Override
    public OutDishDto createDish(InDishDto inDishDto) {
        Dish dish = dishMapper.convertInToDomain(inDishDto);

        Dish savedDish;
        try {
            savedDish = dishRepository.save(dish);
        } catch (DataAccessException e) {
            log.error("Repository exception at dish creation: {}", e.getMessage());
            throw new UnexpectedServiceException("Create dish error", e);
        }

        OutDishDto outDishDto = dishMapper.convertDomainToOut(savedDish);
        log.debug("Create Dish response: {}", outDishDto);
        return outDishDto;
    }

    @Override
    public OutDishDto findDishById(Long id) {
        Dish dish = getDish(id);

        return dishMapper.convertDomainToOut(dish);
    }

    private Dish getDish(Long id) {
        Optional<Dish> dish;
        try {
            dish = dishRepository.findById(id);
        } catch (DataAccessException e) {
            log.error("Unexpected repository error", e);
            throw new UnexpectedServiceException(e);
        }

        if (dish.isEmpty()) {
            throw new ServiceEntityNotFoundException("Dish entity not found");
        }
        return dish.get();
    }

    @Override
    public Dish findDishByIdDomain(Long id) {
        return getDish(id);
    }

    @Override
    public PaginatedData<OutDishDto> findDishesPageFromAMenu(List<Long> ids,
        PaginationCriteria page,
        SortCriteria sort,
        Long menuId
    ) {
        SortingUtil.validateSortCriteria(sort, Dish.class);

        var pageable = PageRequest.of(page.getPage(), page.getLimit(), sort.getOrder(),
            sort.getSortBy());

        Page<Dish> dishesPage;
        try {
            dishesPage = dishRepository.findAllByIdIn(ids, pageable);
        } catch (DataAccessException e) {
            throw new UnexpectedRepositoryException(e);
        }

        var pagination = PaginationUtil.extractPageData(dishesPage,
            String.format("/api/menus/%d/dishes", menuId), sort);

        var dishes = dishesPage.getContent();
        var outDishDtos = dishMapper.convertDomainsToOuts(dishes);

        return new PaginatedData<>(outDishDtos, pagination);
    }

    @Override
    public PaginatedData<OutDishDto> findAllDishes(PaginationCriteria paginationCriteria,
        SortCriteria sort) {
        SortingUtil.validateSortCriteria(sort, Dish.class);

        Pageable pageable = PageRequest.of(paginationCriteria.getPage(),
            paginationCriteria.getLimit(), sort.getOrder(),
            sort.getSortBy());
        Page<Dish> pageDishes;
        try {
            pageDishes = dishRepository.findAll(pageable);
        } catch (DataAccessException e) {
            log.error("Repository exception at find all: {}", e.getMessage());
            throw new UnexpectedServiceException("Find all repository exception", e);
        }
        var pagination = PaginationUtil.extractPageData(pageDishes, "/api/dishes", sort);

        var dishes = pageDishes.getContent();
        var outDishDtos = dishMapper.convertDomainsToOuts(dishes);

        return new PaginatedData<>(outDishDtos, pagination);
    }

    @Override
    public PaginatedData<OutDishDto> findDishesByName(String name,
        PaginationCriteria paginationCriteria,
        SortCriteria sort) {

        SortingUtil.validateSortCriteria(sort, Dish.class);

        Pageable pageable = PageRequest.of(paginationCriteria.getPage(),
            paginationCriteria.getLimit(), sort.getOrder(),
            sort.getSortBy());

        Page<Dish> paginatedDishes;
        try {
            paginatedDishes = dishRepository.findAllByNameContains(name, pageable);
        } catch (DataAccessException e) {
            log.error("Repository exception at find by name: {}", e.getMessage());
            throw new UnexpectedServiceException("Find by name repo exception", e);
        }
        var pagination = PaginationUtil.extractPageData(paginatedDishes, "/api/dishes", sort);

        var dishes = paginatedDishes.getContent();
        var outDishDtos = dishMapper.convertDomainsToOuts(dishes);

        return new PaginatedData<OutDishDto>(outDishDtos, pagination);
    }

    @Override
    public OutDishDto updateDishById(Long id, InDishDto inDishDto) {
        Optional<Dish> optionalDish;
        try {
            optionalDish = dishRepository.findById(id);
        } catch (DataAccessException e) {
            log.error("Repository error", e);
            throw new UnexpectedServiceException(e);
        }

        if (optionalDish.isEmpty()) {
            throw new ServiceEntityNotFoundException("Dish entity not found");
        }

        Dish updateDish = optionalDish.get();
        dishMapper.convertToUpdate(inDishDto, updateDish);

        try {
            dishRepository.save(updateDish);
        } catch (DataAccessException e) {
            log.error("Update dish error", e);
            throw new UnexpectedServiceException(e);
        }

        return dishMapper.convertDomainToOut(updateDish);
    }

    @Override
    public void deleteDishById(Long id) {
        Optional<Dish> dishOptional;

        try {
            dishOptional = dishRepository.findById(id);
        } catch (DataAccessException e) {
            log.error("Unexpected repository error", e);
            throw new UnexpectedServiceException(e);
        }

        if (dishOptional.isEmpty()) {
            throw new ServiceEntityNotFoundException("Entity not found");
        }

        try {
            dishRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new UnexpectedServiceException(e);
        }
    }
}
