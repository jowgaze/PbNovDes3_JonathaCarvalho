package br.com.compass.ticketmanagement.dtos.ticket;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TicketRequestDto {
    @NotNull
    @CPF
    private String cpf;

    @NotNull
    @Size(min = 2)
    private String customerName;

    @NotNull
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(?:\\.[a-zA-Z]{2,})?$")
    private String customerMail;

    @NotNull
    private String eventId;
    private String eventName;

    @NotNull
    private String brlAmount;

    @NotNull
    private String usdAmount;
}
