package br.com.compass.eventmanagement.controllers.exception;

import br.com.compass.eventmanagement.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodArgumentNotValidException(HttpServletRequest request, BindingResult br) {
        String message = "field validation error";

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new StandardError(request, HttpStatus.UNPROCESSABLE_ENTITY, message, br));
    }

    @ExceptionHandler({FeignNotFoundException.class, EventNotFoundException.class})
    public ResponseEntity<StandardError> notFoundException(RuntimeException e, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new StandardError(request, HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(TicketServiceException.class)
    public ResponseEntity<StandardError> ticketServiceException(RuntimeException e, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new StandardError(request, HttpStatus.SERVICE_UNAVAILABLE, e.getMessage()));
    }

    @ExceptionHandler({FeignRequestException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<StandardError> invalidFieldsException(RuntimeException e, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new StandardError(request, HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage()));
    }

    @ExceptionHandler(TicketLinkedException.class)
    public ResponseEntity<StandardError> ticketLinkedException(RuntimeException e, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new StandardError(request, HttpStatus.CONFLICT, e.getMessage()));
    }
}
