package com.hbt.cliente.api.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ApiException extends Throwable {

    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
}
