package br.com.compass.ticketmanagement.exceptions;

public class FeignNotFoundException extends RuntimeException {
    public FeignNotFoundException(String message) {
        super(message);
    }
}
