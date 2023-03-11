package com.example.authjwt.handler;

import com.example.authjwt.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleException(BadCredentialsException exception, WebRequest webRequest) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        final ErrorDto errorDto = ErrorDto.builder()
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errors(Collections.emptyList())
                .build();
        return new ResponseEntity<>(errorDto, badRequest);
    }

}
