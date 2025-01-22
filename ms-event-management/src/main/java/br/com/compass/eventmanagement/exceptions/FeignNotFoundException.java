package br.com.compass.eventmanagement.exceptions;

public class FeignNotFoundException extends RuntimeException {
    public FeignNotFoundException(String message) {
        super(message);
    }
}
