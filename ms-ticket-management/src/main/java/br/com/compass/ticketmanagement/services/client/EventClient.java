package br.com.compass.ticketmanagement.services.client;

import br.com.compass.ticketmanagement.dtos.event.EventResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-event", url = "localhost:8080/br/com/compass/eventmanagement/v1")
public interface EventClient {
    @GetMapping("/get-event/{id}")
    EventResponseDto findById(@PathVariable("id") String id);
}
