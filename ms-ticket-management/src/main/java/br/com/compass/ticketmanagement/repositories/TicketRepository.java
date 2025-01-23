package br.com.compass.ticketmanagement.repositories;

import br.com.compass.ticketmanagement.domain.ticket.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findByCpf(String cpf);
}
