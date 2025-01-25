package br.com.compass.eventmanagement.services;

import br.com.compass.eventmanagement.domain.address.Address;
import br.com.compass.eventmanagement.dtos.viacep.ViaCepResponseDto;
import br.com.compass.eventmanagement.exceptions.FeignNotFoundException;
import br.com.compass.eventmanagement.exceptions.FeignRequestException;
import br.com.compass.eventmanagement.repositories.AddressRepository;
import br.com.compass.eventmanagement.services.client.viacep.ViaCepClient;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static br.com.compass.eventmanagement.common.AddressConstants.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @InjectMocks
    private AddressService service;

    @Mock
    private AddressRepository repository;

    @Mock
    private ViaCepClient client;

    @Test
    public void getAddress_ByAddressSaved_ReturnsAddress() {
        when(repository.findById("01001-000")).thenReturn(Optional.of(ADDRESS_SAVED));

        Address sut = service.findById("01001000");

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(ADDRESS_SAVED);
    }

    @Test
    public void insertAddress_ByExistentZipCode_ReturnsAddress() {
        String zipCode = "01001000";
        when(client.getViaCep(zipCode)).thenReturn(VIACEP_RESPONSE);
        when(repository.insert(ADDRESS_SAVED)).thenReturn(ADDRESS_SAVED);

        Address sut = service.insert(zipCode);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(ADDRESS_SAVED);
    }

    @Test
    public void insertAddress_ByNonExistentZipCode_ThrowsException() {
        String zipCode = "00000000";
        ViaCepResponseDto viaCepResponse = new ViaCepResponseDto();
        viaCepResponse.setErro("true");
        when(client.getViaCep(zipCode)).thenReturn(viaCepResponse);

        assertThatThrownBy(() -> service.insert(zipCode)).isInstanceOf(FeignNotFoundException.class);
    }

    @Test
    public void insertAddress_ByInvalidZipCode_ThrowsException() {
        String zipCode = "000 A112";
        when(client.getViaCep(zipCode)).thenThrow(FeignException.BadRequest.class);
        assertThatThrownBy(() -> service.insert(zipCode))
                .isInstanceOf(FeignRequestException.class)
                .hasMessageContaining("invalid fields - no lost letters or white space");
    }
}
