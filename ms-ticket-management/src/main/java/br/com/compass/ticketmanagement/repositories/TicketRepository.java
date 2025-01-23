package br.com.compass.ticketmanagement.repositories;

import br.com.compass.ticketmanagement.domain.ticket.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRepository extends MongoRepository<Ticket, Long> {
}
