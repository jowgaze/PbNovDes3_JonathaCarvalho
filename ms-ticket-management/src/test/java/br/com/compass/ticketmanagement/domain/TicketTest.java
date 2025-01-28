package br.com.compass.ticketmanagement.domain;

import br.com.compass.ticketmanagement.domain.ticket.Ticket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(SpringExtension.class)
public class TicketTest {

    @Test
    public void ticketEquals() {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        Ticket ticket3 = new Ticket();
        ticket1.setId("1");
        ticket2.setId("1");
        ticket3.setId("3");

        assertEquals(ticket1, ticket2);
        assertNotEquals(ticket1, ticket3);
        assertNotEquals(new Object(), ticket1);
        assertNotEquals(null, ticket2);
        assertEquals(ticket1.hashCode(), ticket2.hashCode());
        assertNotEquals(ticket1.hashCode(), ticket3.hashCode());
    }
}
