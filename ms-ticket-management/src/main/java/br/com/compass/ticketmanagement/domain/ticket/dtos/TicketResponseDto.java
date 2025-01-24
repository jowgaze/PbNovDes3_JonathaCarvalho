package br.com.compass.ticketmanagement.domain.ticket.dtos;

import br.com.compass.ticketmanagement.services.client.dtos.EventResponseDto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TicketResponseDto {
    private String id;
    private String cpf;
    private String customerName;
    private String customerMail;
    private EventResponseDto event;
    private String brlAmount;
    private String usdAmount;
}
