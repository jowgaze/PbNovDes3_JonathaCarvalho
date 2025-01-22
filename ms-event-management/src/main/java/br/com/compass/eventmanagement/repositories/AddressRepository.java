package br.com.compass.eventmanagement.repositories;

import br.com.compass.eventmanagement.domain.address.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepository extends MongoRepository<Address, String> {
}
