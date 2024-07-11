package com.example.spring.Config;


import com.example.spring.Persistence.Model.Dto.ExeptionResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ExeptionResponse.class)
    public ResponseEntity<Map<String, String>> ReponseExGen(ExeptionResponse ex) {
        Map<String, String> message = new HashMap<>();
        message.put(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(message, ex.getHttp());
    }


    // Exception of jwt at filter level
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Map<String, String>> handleExpiredJwtException(JwtException e, WebRequest request) {
        Map<String, String> messageError = new HashMap<>();
        String error = e.getMessage().substring(0, e.getMessage().indexOf(":"));

        if (error != null) {
            messageError.put("JWT", error);
            return new ResponseEntity<>(messageError, HttpStatus.NOT_FOUND);
        }

        messageError.put("jwt", "Error JWT. Consult Support");
        return new ResponseEntity<>(messageError, HttpStatus.NOT_FOUND);
    }


    // exeptions for validations on forms login and register
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> ValidateError(MethodArgumentNotValidException e) {
        Map<String, String> response = extractMessageErrorsDTO(e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //Extraction of error message, from the validation of the forms.
    private Map<String, String> extractMessageErrorsDTO(BindingResult result) {
        Map<String, String> response = new HashMap<>();
        for (FieldError item : result.getFieldErrors()) {
            response.put(item.getField().toUpperCase(), item.getDefaultMessage().toUpperCase());
        }
        return response;
    }
}
