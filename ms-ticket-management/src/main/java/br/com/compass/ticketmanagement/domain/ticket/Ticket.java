package br.com.compass.ticketmanagement.domain.ticket;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

@Document(collection = "ticket")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Ticket implements Serializable {
    @Id
    private String id;
    private String cpf;
    private String customerName;
    private String customerMail;
    private String eventId;
    private String eventName;
    private String brlAmount;
    private String usdAmount;
    private Boolean deleted = false;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
