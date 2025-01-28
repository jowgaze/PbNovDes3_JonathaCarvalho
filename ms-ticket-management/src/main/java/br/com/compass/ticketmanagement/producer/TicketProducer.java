package br.com.compass.ticketmanagement.producer;

import br.com.compass.ticketmanagement.dtos.ticket.TicketResponseDto;
import br.com.compass.ticketmanagement.exceptions.EmailConversionException;
import br.com.compass.ticketmanagement.dtos.email.EmailDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketProducer {
    private final RabbitTemplate rabbitTemplate;
    private final Queue ticketQueue;

    public void purchaseConfirmation(TicketResponseDto ticket) {
        EmailDto email = generateEmail(ticket);
        String emailJson = emailToJson(email);
        rabbitTemplate.convertAndSend(ticketQueue.getName(), emailJson);
    }

    private EmailDto generateEmail(TicketResponseDto ticket) {
        String sender = "ticket@mail.com";
        String recipient = ticket.getCustomerMail();
        String subject = "Confirmação de compra";
        String body = bodyBuilder(ticket);

        return new EmailDto(sender, recipient, subject, body);
    }

    private String bodyBuilder(TicketResponseDto ticket) {
        return String.format(
                "Olá, %s! A compra do seu ingresso, com código %s, para %s foi confirmada.",
                ticket.getCustomerName(),
                ticket.getId(),
                ticket.getEvent().getName()
        );
    }

    private String emailToJson(EmailDto email) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(email);
        } catch (JsonProcessingException e) {
            throw new EmailConversionException("error converting email to JSON");
        }
    }
}
