package br.com.compass.eventmanagement.exceptions;

public class TicketServiceException extends RuntimeException {
  public TicketServiceException(String message) {
    super(message);
  }
}
