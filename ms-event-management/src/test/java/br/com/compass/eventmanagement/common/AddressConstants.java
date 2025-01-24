package br.com.compass.eventmanagement.common;

import br.com.compass.eventmanagement.domain.address.Address;
import br.com.compass.eventmanagement.dtos.viacep.ViaCepResponseDto;

public class AddressConstants {
    public static final Address ADDRESS_SAVED = new Address("01001-000", "Praça da Sé", "Sé", "São Paulo", "SP");
    public static final ViaCepResponseDto VIACEP_RESPONSE = new ViaCepResponseDto(
            "01001-000", "Praça da Sé", "lado ímpar", "",
            "Sé", "São Paulo", "SP", "São Paulo", "Sudeste",
            "3550308", "1004", "11", "7107", null
    );
}
