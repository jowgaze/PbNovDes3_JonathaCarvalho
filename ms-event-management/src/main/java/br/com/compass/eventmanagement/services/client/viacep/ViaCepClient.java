package br.com.compass.eventmanagement.services.client.viacep;

import br.com.compass.eventmanagement.dtos.viacep.ViaCepResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws/")
public interface ViaCepClient {

    @GetMapping("{cep}/json/")
    ViaCepResponseDto getViaCep(@PathVariable String cep);
}
