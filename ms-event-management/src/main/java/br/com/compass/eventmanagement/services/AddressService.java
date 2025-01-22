package br.com.compass.eventmanagement.services;

import br.com.compass.eventmanagement.domain.address.Address;
import br.com.compass.eventmanagement.exceptions.FeignNotFoundException;
import br.com.compass.eventmanagement.exceptions.FeignRequestException;
import br.com.compass.eventmanagement.repositories.AddressRepository;
import br.com.compass.eventmanagement.services.client.viacep.ViaCepClient;
import br.com.compass.eventmanagement.services.client.viacep.ViaCepResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

            if (response.getErro() != null)
                throw new FeignNotFoundException("address not found");

            Address address = new Address(response);
            return addressRepository.insert(address);
        } catch (FeignException.BadRequest e) {
            throw new FeignRequestException("invalid fields: only numbers");
        }
    }

    private String zipCodeFormatter(String zipCode) {
        return zipCode.substring(0, 5) + "-" + zipCode.substring(5);
    }
}
