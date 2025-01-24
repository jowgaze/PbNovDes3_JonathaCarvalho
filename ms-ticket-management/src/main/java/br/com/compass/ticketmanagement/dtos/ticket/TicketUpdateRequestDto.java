package br.com.compass.ticketmanagement.dtos.ticket;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TicketUpdateRequestDto {
    @CPF
    private String cpf;

    @Size(min = 2)
    private String customerName;

    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(?:\\.[a-zA-Z]{2,})?$")
    private String customerMail;
    private String brlAmount;
    private String usdAmount;
}

