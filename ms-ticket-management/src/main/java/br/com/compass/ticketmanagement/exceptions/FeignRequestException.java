package br.com.compass.ticketmanagement.exceptions;

public class FeignRequestException extends RuntimeException {
  public FeignRequestException(String message) {
    super(message);
  }
}
