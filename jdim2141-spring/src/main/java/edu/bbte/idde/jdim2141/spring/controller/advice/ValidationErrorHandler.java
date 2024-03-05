package edu.bbte.idde.jdim2141.spring.controller.advice;

import edu.bbte.idde.jdim2141.spring.model.dto.out.ErrorConstraintViolationDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.ErrorMessageDto;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class ValidationErrorHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorConstraintViolationDto handleConstraintViolation(
        ConstraintViolationException e) {
        List<String> violations = e
            .getConstraintViolations()
            .stream()
            .map(it -> it.getPropertyPath().toString() + " " + it.getMessage())
            .toList();

        String message = "Constraint violation";

        return new ErrorConstraintViolationDto(message, violations);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorMessageDto handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        log.info("Http message not readable", e);
        String message = String.format("Malformed request. Check the request payload. %s",
            e.getMessage());

        return new ErrorMessageDto(message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorMessageDto handleMethodArgumentMismatch(
        MethodArgumentTypeMismatchException e) {
        log.info("Method argument type mismatch", e);
        String message = String.format("Invalid parameter: %s", e.getPropertyName());

        return new ErrorMessageDto(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorConstraintViolationDto handleMethodArgumentNotValid(
        MethodArgumentNotValidException e) {
        log.info("MethodArgument exception occurred: {}", e.getMessage());
        List<String> result = e
            .getBindingResult()
            .getFieldErrors()
            .stream()
            .map(it ->
                it.getField() + " " + it.getDefaultMessage()
            ).toList();
        String message = "Argument type violation";
        return new ErrorConstraintViolationDto(message, result);
    }

}
