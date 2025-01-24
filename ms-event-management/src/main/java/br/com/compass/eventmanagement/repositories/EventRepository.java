package br.com.compass.eventmanagement.repositories;

import br.com.compass.eventmanagement.domain.event.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
}
