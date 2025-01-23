package br.com.compass.ticketmanagement.controllers.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
public class StandardError {
    private String path;
    private String method;
    private String statusText;
    private Integer status;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    public StandardError(HttpServletRequest request, HttpStatus status, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.statusText = status.getReasonPhrase();
        this.status = status.value();
        this.message = message;
    }

    public StandardError(HttpServletRequest request, HttpStatus status, String message, BindingResult bindingResult) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.statusText = status.getReasonPhrase();
        this.status = status.value();
        this.message = message;
        addErros(bindingResult);
    }

    private void addErros(BindingResult bindingResult) {
        errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
