package edu.bbte.idde.jdim2141.spring.controller;

import edu.bbte.idde.jdim2141.spring.model.dto.in.InUserLoginDto;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InUserRegisterDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.MessageDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutUserDto;
import edu.bbte.idde.jdim2141.spring.service.AuthService;
import edu.bbte.idde.jdim2141.spring.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public OutUserDto login(@RequestBody @Validated InUserLoginDto inUserLoginDto,
        HttpServletResponse response) {
        log.debug("POST /api/auth/login endpoint reached");

        var user = authService.login(inUserLoginDto);
        var jwt = jwtService.provideToken(user);
        response.addCookie(addJwtCookie(jwt));

        return user;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public OutUserDto register(@RequestBody @Validated InUserRegisterDto inUserRegisterDto,
        HttpServletResponse response) {
        log.debug("POST /api/auth/register endpoint reached");

        var user = authService.register(inUserRegisterDto);
        var jwt = jwtService.provideToken(user);
        response.addCookie(addJwtCookie(jwt));

        return user;
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public MessageDto register(HttpServletResponse response) {
        log.debug("POST /api/auth/logout");
        response.addCookie(removeJwtCookie());

        return new MessageDto("Successful logout");
    }

    @GetMapping("/introspect")
    @ResponseStatus(HttpStatus.OK)
    public OutUserDto getUserData(@AuthenticationPrincipal Jwt jwt) {
        log.info("/introspect endpoint reached");

        Long userId = Long.parseLong(jwt.getSubject());

        return authService.getUserData(userId);
    }

    private Cookie addJwtCookie(Jwt jwt) {
        var cookie = new Cookie("jwt", jwt.getTokenValue());
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        var maxAge = (int) Duration.between(Instant.now(), jwt.getExpiresAt()).getSeconds();
        cookie.setMaxAge(maxAge);

        return cookie;
    }

    private Cookie removeJwtCookie() {
        var cookie = new Cookie("jwt", "");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);

        return cookie;
    }
}
