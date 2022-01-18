package com.hbt.cliente.api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseData<T> {

    private HttpStatus status;
    private int count;
    private String message;
    private ZonedDateTime timestamp;
    private T payload;

}
