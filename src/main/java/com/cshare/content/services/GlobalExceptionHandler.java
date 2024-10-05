package com.cshare.content.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException.BadRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import com.cshare.content.dto.ErrorDto;
import com.cshare.content.exceptions.NotFoundException;
import com.cshare.content.exceptions.PermissionException;

import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseStatusExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public Mono<ResponseEntity<ErrorDto>> handleNotFoundException(NotFoundException ex, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(ex.getMessage())));
    }

    @ExceptionHandler(PermissionException.class)
    public Mono<ResponseEntity<ErrorDto>> handlePermissionException(PermissionException ex, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto(ex.getMessage())));
    }

    @ExceptionHandler(ServerWebInputException.class)
    public Mono<ResponseEntity<ErrorDto>> handleBadRequestException(ServerWebInputException ex, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.status(ex.getStatusCode()).body(new ErrorDto(ex.getReason())));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorDto>> handleGenericException(Exception ex, ServerWebExchange exchange) {
        System.out.println(ex.getClass().getSuperclass());
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("Internal Server Error")));
    }
}
