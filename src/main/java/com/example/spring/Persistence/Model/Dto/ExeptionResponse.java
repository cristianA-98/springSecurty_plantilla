package com.example.spring.Persistence.Model.Dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExeptionResponse extends RuntimeException {
    private HttpStatus http;
    private String code;

    public ExeptionResponse(String message, HttpStatus http, String code) {
        super(message);
        this.http = http;
        this.code = code;
    }
}
