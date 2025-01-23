package br.com.compass.ticketmanagement.repositories;

import br.com.compass.ticketmanagement.domain.ticket.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findByCpf(String cpf);

    Optional<Ticket> findByIdAndDeletedFalse(String id);
}
