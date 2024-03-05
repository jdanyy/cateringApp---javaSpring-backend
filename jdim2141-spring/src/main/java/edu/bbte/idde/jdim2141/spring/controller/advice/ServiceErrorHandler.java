package edu.bbte.idde.jdim2141.spring.controller.advice;

import edu.bbte.idde.jdim2141.spring.exception.service.InvalidCredentialsException;
import edu.bbte.idde.jdim2141.spring.exception.service.InvalidSortPropertyException;
import edu.bbte.idde.jdim2141.spring.exception.service.ServiceEntityConflictException;
import edu.bbte.idde.jdim2141.spring.exception.service.ServiceEntityNotFoundException;
import edu.bbte.idde.jdim2141.spring.exception.service.UnexpectedServiceException;
import edu.bbte.idde.jdim2141.spring.model.dto.out.ErrorMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ServiceErrorHandler {

    @ExceptionHandler(UnexpectedServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ErrorMessageDto handleUnexpectedServiceException(UnexpectedServiceException e) {
        log.debug("Unexpected Service Error: {}", e.getMessage());

        String message = "Unexpected server error occurred";
        return new ErrorMessageDto(message);
    }

    @ExceptionHandler(ServiceEntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorMessageDto handleEntityNotFoundException(ServiceEntityNotFoundException e) {
        log.debug("Entity not found error: {}", e.getMessage());

        return new ErrorMessageDto(e.getMessage());
    }

    @ExceptionHandler(ServiceEntityConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public final ErrorMessageDto handleEntityConflictException(ServiceEntityConflictException e) {
        log.debug("Service Entity conflict: {}", e.getMessage());

        return new ErrorMessageDto("This user already exists");
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorMessageDto handleInvalidCredentialsException(InvalidCredentialsException e) {
        log.debug("Invalid credentials on login: {}", e.getMessage());

        return new ErrorMessageDto("Invalid credentials");
    }

    @ExceptionHandler(InvalidSortPropertyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ErrorMessageDto handleInvalidSortingPropertyException(
        InvalidSortPropertyException e) {
        log.debug("Invalid sorting attribute");

        String message = "Invalid sorting attribute. " + e.getMessage();
        return new ErrorMessageDto(message);
    }
}
