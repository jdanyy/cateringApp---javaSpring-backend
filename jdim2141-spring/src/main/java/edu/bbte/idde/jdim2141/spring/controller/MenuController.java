package edu.bbte.idde.jdim2141.spring.controller;


import static edu.bbte.idde.jdim2141.spring.config.OpenApiConfiguration.BEARER_AUTH_TOKEN;

import edu.bbte.idde.jdim2141.spring.constants.MenuPermission;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InMenuDto;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
@CrossOrigin
@SecurityRequirement(name = BEARER_AUTH_TOKEN)
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public OutMenuDto findMenuById(@PathVariable Long menuId) {
        log.debug("GET /api/menus/{} endpoint reached", menuId);
        return menuService.findMenuById(menuId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OutMenuDto createMenu(@RequestBody @Valid InMenuDto menuDto,
        @AuthenticationPrincipal Jwt jwt) {
        log.debug("POST /api/menus endpoint reached");
        Long userId = Long.parseLong(jwt.getSubject());

        return menuService.createMenu(menuDto, userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PaginatedData<OutMenuDto> findAllMenus(
        @RequestParam(defaultValue = "CONSUMER") MenuPermission permission,
        @Valid PaginationCriteria page,
        SortCriteria sort,
        @AuthenticationPrincipal Jwt jwt
    ) {
        log.debug("GET /api/menus endpoint reached");
        Long userId = Long.parseLong(jwt.getSubject());
        return menuService.findAllMenu(userId, permission, page, sort);
    }

    @GetMapping(params = {"menuTitle"})
    @ResponseStatus(HttpStatus.OK)
    public PaginatedData<OutMenuDto> findAllMenusByTitle(
        @RequestParam(defaultValue = "CONSUMER") MenuPermission permission,
        @RequestParam String menuTitle,
        @Valid PaginationCriteria page, SortCriteria sort,
        @AuthenticationPrincipal Jwt jwt
    ) {
        log.debug("GET /api/menus?menuTitle={}", menuTitle);
        Long userId = Long.parseLong(jwt.getSubject());
        return menuService.findMenuByMenuTitle(userId, permission, menuTitle, page, sort);
    }

    @PutMapping("/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public OutMenuDto updateMenuById(@PathVariable Long menuId,
        @RequestBody @Valid InMenuDto menuDto, @AuthenticationPrincipal Jwt jwt) {
        log.debug("PUT /api/menus/{}", menuId);
        Long userId = Long.parseLong(jwt.getSubject());
        return menuService.updateMenuById(menuId, menuDto, userId);
    }

    @DeleteMapping("/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuById(@PathVariable Long menuId, @AuthenticationPrincipal Jwt jwt) {
        log.debug("DELETE /api/menus/{}", menuId);
        Long userId = Long.parseLong(jwt.getSubject());

        menuService.deleteMenuById(menuId, userId);
    }

}
