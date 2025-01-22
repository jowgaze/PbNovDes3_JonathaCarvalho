package br.com.compass.eventmanagement.exceptions;

public class FeignRequestException extends RuntimeException {
  public FeignRequestException(String message) {
    super(message);
  }
}
