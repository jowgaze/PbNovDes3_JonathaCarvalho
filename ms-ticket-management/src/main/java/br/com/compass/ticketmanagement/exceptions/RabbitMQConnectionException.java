package br.com.compass.ticketmanagement.exceptions;

public class RabbitMQConnectionException extends RuntimeException {
  public RabbitMQConnectionException(String message) {
    super(message);
  }
}
