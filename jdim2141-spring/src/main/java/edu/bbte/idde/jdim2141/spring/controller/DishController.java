package edu.bbte.idde.jdim2141.spring.controller;

import static edu.bbte.idde.jdim2141.spring.config.OpenApiConfiguration.BEARER_AUTH_TOKEN;

import edu.bbte.idde.jdim2141.spring.model.dto.in.InDishDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutDishDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.PaginatedData;
import edu.bbte.idde.jdim2141.spring.model.dto.request.PaginationCriteria;
import edu.bbte.idde.jdim2141.spring.model.dto.request.SortCriteria;
import edu.bbte.idde.jdim2141.spring.service.DishService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/dishes")
@RequiredArgsConstructor
@CrossOrigin
@SecurityRequirement(name = BEARER_AUTH_TOKEN)
public class DishController {

    private final DishService dishService;

    @GetMapping("/{dishId}")
    @ResponseStatus(HttpStatus.OK)
    public OutDishDto findDishById(@PathVariable Long dishId) {
        log.debug("GET /api/dishes/{}", dishId);
        return dishService.findDishById(dishId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PaginatedData<OutDishDto> findAllDishes(@Valid PaginationCriteria page,
        SortCriteria sort) {
        log.debug("GET /api/dishes");
        return dishService.findAllDishes(page, sort);
    }

    @GetMapping(params = {"dishName"})
    @ResponseStatus(HttpStatus.OK)
    public PaginatedData<OutDishDto> findAllDishesByName(@Valid PaginationCriteria page,
        SortCriteria sort, String dishName) {
        log.debug("GET /api/dishes?dishName={}", dishName);
        return dishService.findDishesByName(dishName, page, sort);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OutDishDto createDish(@RequestBody @Valid InDishDto inDishDto) {
        log.debug("POST /api/dishes");
        return dishService.createDish(inDishDto);
    }

    @PutMapping("/{dishId}")
    @ResponseStatus(HttpStatus.OK)
    public OutDishDto updateDishById(@PathVariable Long dishId,
        @RequestBody @Valid InDishDto inDishDto) {
        log.debug("PUT /api/menus/{}", dishId);
        return dishService.updateDishById(dishId, inDishDto);
    }

    @DeleteMapping("/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDishById(@PathVariable Long dishId) {
        log.debug("DELETE /api/dishes/{}", dishId);

        dishService.deleteDishById(dishId);
    }


}
