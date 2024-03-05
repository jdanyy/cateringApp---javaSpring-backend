package edu.bbte.idde.jdim2141.spring.service;

import edu.bbte.idde.jdim2141.spring.constants.UserRole;
import edu.bbte.idde.jdim2141.spring.exception.repository.UnexpectedRepositoryException;
import edu.bbte.idde.jdim2141.spring.exception.service.InvalidCredentialsException;
import edu.bbte.idde.jdim2141.spring.exception.service.ServiceEntityConflictException;
import edu.bbte.idde.jdim2141.spring.exception.service.ServiceEntityNotFoundException;
import edu.bbte.idde.jdim2141.spring.exception.service.UnexpectedServiceException;
import edu.bbte.idde.jdim2141.spring.mapper.UserMapper;
import edu.bbte.idde.jdim2141.spring.model.domain.Role;
import edu.bbte.idde.jdim2141.spring.model.domain.User;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InLangDto;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InThemeDto;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InUserLoginDto;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InUserRegisterDto;
import edu.bbte.idde.jdim2141.spring.model.dto.in.InUserRoleDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutUserDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.PaginatedData;
import edu.bbte.idde.jdim2141.spring.model.dto.request.PaginationCriteria;
import edu.bbte.idde.jdim2141.spring.model.dto.request.SortCriteria;
import edu.bbte.idde.jdim2141.spring.repository.RoleRepository;
import edu.bbte.idde.jdim2141.spring.repository.UserRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public OutUserDto loginUser(InUserLoginDto inUserLoginDto) {
        User user = userMapper.convertLoginInToDomain(inUserLoginDto);

        Optional<User> optionalUser;
        try {
            optionalUser = userRepository.findByEmail(user.getEmail());
        } catch (DataAccessException e) {
            log.error("Error while finding user", e);
            throw new UnexpectedServiceException(e);
        }

        if (optionalUser.isEmpty()) {
            throw new ServiceEntityNotFoundException("User not found");
        }

        User savedUser = optionalUser.get();
        String passwordHash = savedUser.getPasswordHash();
        String password = inUserLoginDto.getPassword();

        if (!passwordEncoder.matches(password, passwordHash)) {
            throw new InvalidCredentialsException("Password doesnt match");
        }

        return userMapper.convertDomainToOut(savedUser);
    }

    public OutUserDto registerUser(InUserRegisterDto inUserRegisterDto) {
        Optional<User> optionalUser;
        try {
            optionalUser = userRepository.findByEmail(inUserRegisterDto.getEmail());
        } catch (DataAccessException e) {
            log.error("Error while user query", e);
            throw new UnexpectedServiceException(e);
        }

        if (optionalUser.isPresent()) {
            throw new ServiceEntityConflictException("User already exists");
        }

        User userToSave = userMapper.convertRegisterInToDomain(inUserRegisterDto);
        String passwordHash = passwordEncoder.encode(inUserRegisterDto.getPassword());
        userToSave.setPasswordHash(passwordHash);

        Role clientRole;
        try {
            clientRole = roleRepository.findRoleByName(UserRole.CLIENT);
        } catch (DataAccessException e) {
            log.error("Error while finding a role", e);
            throw new UnexpectedServiceException(e);
        }
        userToSave.setRoles(List.of(clientRole));

        return createUser(userToSave);
    }

    public OutUserDto createUser(User user) {
        User savedUser;
        try {
            savedUser = userRepository.save(user);
        } catch (DataAccessException e) {
            throw new UnexpectedServiceException(e);
        }

        return userMapper.convertDomainToOut(savedUser);
    }

    public OutUserDto getUser(Long id) {
        var domainUser = getDomainUserById(id);

        return userMapper.convertDomainToOut(domainUser);
    }

    public User getDomainUserById(Long id) {
        Optional<User> user;

        try {
            user = userRepository.findById(id);
        } catch (DataAccessException e) {
            log.error("Error finding user by email", e);
            throw new UnexpectedServiceException(e);
        }

        if (user.isEmpty()) {
            throw new ServiceEntityNotFoundException("User Not found");
        }

        return user.get();
    }

    public OutUserDto findUserById(Long id) {
        User user = getDomainUserById(id);

        return userMapper.convertDomainToOut(user);
    }

    public PaginatedData<OutUserDto> findAllUsers(PaginationCriteria page, SortCriteria sort) {
        SortingUtil.validateSortCriteria(sort, User.class);

        Pageable pageable = PageRequest.of(page.getPage(), page.getLimit(), sort.getOrder(),
            sort.getSortBy());

        Page<User> pageUsers;

        try {
            pageUsers = userRepository.findAll(pageable);
        } catch (DataAccessException e) {
            throw new UnexpectedRepositoryException(e);
        }
        var pagination = PaginationUtil.extractPageData(pageUsers, "/api/users", sort);

        var users = pageUsers.getContent();
        var outUserDtos = userMapper.convertDomainsToOuts(users);

        var paginatedData = new PaginatedData<OutUserDto>();
        paginatedData.setPagination(pagination);
        paginatedData.setContent(outUserDtos);
        return paginatedData;
    }

    public OutUserDto updateUserPermission(Long userId, InUserRoleDto inUserRoleDto) {
        User user = getDomainUserById(userId);

        userMapper.convertInUserRoleToDomain(inUserRoleDto, user);

        try {
            userRepository.save(user);
        } catch (DataAccessException e) {
            throw new UnexpectedRepositoryException(e);
        }

        return userMapper.convertDomainToOut(user);
    }

    public OutUserDto updateUserTheme(Long userId, InThemeDto inThemeDto) {
        User user = getDomainUserById(userId);

        user.setTheme(inThemeDto.getTheme());

        try {
            userRepository.save(user);
        } catch (DataAccessException e) {
            throw new UnexpectedServiceException(e);
        }

        return userMapper.convertDomainToOut(user);
    }

    public OutUserDto updateUserLang(Long userId, InLangDto inLangDto) {
        User user = getDomainUserById(userId);

        user.setLang(inLangDto.getLang());

        try {
            userRepository.save(user);
        } catch (DataAccessException e) {
            throw new UnexpectedServiceException(e);
        }

        return userMapper.convertDomainToOut(user);
    }
}
