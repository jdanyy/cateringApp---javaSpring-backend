package edu.bbte.idde.jdim2141.spring.controller;

import static edu.bbte.idde.jdim2141.spring.config.OpenApiConfiguration.BEARER_AUTH_TOKEN;

import edu.bbte.idde.jdim2141.spring.model.dto.in.InLangDto;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InThemeDto;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InUserRoleDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutUserDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.PaginatedData;
import edu.bbte.idde.jdim2141.spring.model.dto.request.PaginationCriteria;
import edu.bbte.idde.jdim2141.spring.model.dto.request.SortCriteria;
import edu.bbte.idde.jdim2141.spring.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = BEARER_AUTH_TOKEN)
@CrossOrigin
public class UserController {

    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PaginatedData<OutUserDto> findAllUsers(PaginationCriteria page, SortCriteria sort) {
        log.debug("GET /api/users");

        return userService.findAllUsers(page, sort);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public OutUserDto findUserById(@PathVariable Long userId) {
        log.debug("GET /api/users/{}", userId);

        return userService.findUserById(userId);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public OutUserDto updateUserPermission(@PathVariable Long userId,
        @RequestBody InUserRoleDto inUserRoleDto) {
        log.debug("GET /api/users/{}", userId);

        return userService.updateUserPermission(userId, inUserRoleDto);
    }

    @PutMapping("/{userId}/theme")
    @ResponseStatus(HttpStatus.OK)
    public OutUserDto updateUserTheme(@PathVariable Long userId,
        @RequestBody InThemeDto inThemeDto) {
        log.debug("GET /api/users/{}/theme", userId);

        return userService.updateUserTheme(userId, inThemeDto);
    }

    @PutMapping("/{userId}/lang")
    @ResponseStatus(HttpStatus.OK)
    public OutUserDto updateUserLanguagePreference(@PathVariable Long userId,
        @RequestBody InLangDto inLangDto) {
        log.debug("GET /api/users/{}/lang", userId);

        return userService.updateUserLang(userId, inLangDto);
    }
}
