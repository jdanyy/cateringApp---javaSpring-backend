package edu.bbte.idde.jdim2141.spring.service;

import edu.bbte.idde.jdim2141.spring.model.dto.in.InUserLoginDto;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InUserRegisterDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutUserDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    public OutUserDto login(InUserLoginDto inUserLoginDto) {
        return userService.loginUser(inUserLoginDto);
    }

    @Transactional
    public OutUserDto register(InUserRegisterDto inUserRegisterDto) {
        return userService.registerUser(inUserRegisterDto);
    }

    public OutUserDto getUserData(Long userId) {
        return userService.getUser(userId);
    }
}
