package br.com.compass.eventmanagement.services;

import br.com.compass.eventmanagement.domain.address.Address;
import br.com.compass.eventmanagement.exceptions.FeignNotFoundException;
import br.com.compass.eventmanagement.exceptions.FeignRequestException;
import br.com.compass.eventmanagement.repositories.AddressRepository;
import br.com.compass.eventmanagement.services.client.viacep.ViaCepClient;
import br.com.compass.eventmanagement.services.client.viacep.ViaCepResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final ViaCepClient viaCepClient;

    @Transactional(readOnly = true)
    public Address findById(String zipCode) {
        String formattedZipCode = zipCodeFormatter(zipCode);

        return addressRepository.findById(formattedZipCode)
                .orElseGet(() -> insert(zipCode));
    }

    @Transactional
    public Address insert(String zipCode) {
        try {
            ViaCepResponse response = viaCepClient.getViaCep(zipCode);

            if (response.getErro() != null) {
                log.error("Address not found. zipCode: {}", zipCode);
                throw new FeignNotFoundException(String.format("address with zipcode=%S not found", zipCode));
            }

            Address address = new Address(response);
            return addressRepository.insert(address);
        } catch (FeignException.BadRequest e) {
            log.error("No lost letters or white space. zipCode: {}", zipCode);
            throw new FeignRequestException("invalid fields - no lost letters or white space");
        }
    }

    private String zipCodeFormatter(String zipCode) {
        return zipCode.substring(0, 5) + "-" + zipCode.substring(5);
    }
}
