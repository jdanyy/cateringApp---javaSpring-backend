package edu.bbte.idde.jdim2141.spring.controller;

import static edu.bbte.idde.jdim2141.spring.config.OpenApiConfiguration.BEARER_AUTH_TOKEN;

import edu.bbte.idde.jdim2141.spring.model.dto.in.InMenuDishDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutDishDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutMenuDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.PaginatedData;
import edu.bbte.idde.jdim2141.spring.model.dto.request.PaginationCriteria;
import edu.bbte.idde.jdim2141.spring.model.dto.request.SortCriteria;
import edu.bbte.idde.jdim2141.spring.service.MenuService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
@CrossOrigin
@SecurityRequirement(name = BEARER_AUTH_TOKEN)
public class MenuDishController {

    private final MenuService menuService;

    @GetMapping("/{menuId}/dishes")
    @ResponseStatus(HttpStatus.OK)
    public PaginatedData<OutDishDto> findDishesForMenu(
        @PathVariable Long menuId,
        @Valid PaginationCriteria page,
        SortCriteria sort
    ) {
        log.debug("GET /api/menus/{}/dishes", menuId);

        return menuService.findDishesForMenu(menuId, page, sort);
    }

    @PostMapping("/{menuId}/dishes")
    @ResponseStatus(HttpStatus.OK)
    public OutMenuDto addDishToMenu(@PathVariable Long menuId,
        @RequestBody @Valid InMenuDishDto inMenuDishDto, @AuthenticationPrincipal Jwt jwt) {
        log.debug("POST /api/menus/{}/dishes", menuId);
        Long userId = Long.parseLong(jwt.getSubject());

        return menuService.addDishToMenu(menuId, inMenuDishDto, userId);
    }

    @DeleteMapping("/{menuId}/dishes/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDishFromMenu(@PathVariable Long menuId, @PathVariable Long dishId,
        @AuthenticationPrincipal Jwt jwt) {
        log.debug("DELETE /api/menus/{}/dishes/{}", menuId, dishId);
        Long userId = Long.parseLong(jwt.getSubject());

        menuService.deleteDishFromMenu(menuId, dishId, userId);
    }

}
