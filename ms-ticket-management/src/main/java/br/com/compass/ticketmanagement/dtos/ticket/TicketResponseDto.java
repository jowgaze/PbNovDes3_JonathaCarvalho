package br.com.compass.ticketmanagement.dtos.ticket;

import br.com.compass.ticketmanagement.dtos.event.EventResponseDto;
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
