package br.com.compass.ticketmanagement.dtos;

import br.com.compass.ticketmanagement.domain.ticket.Ticket;
import br.com.compass.ticketmanagement.dtos.ticket.TicketResponseDto;
import br.com.compass.ticketmanagement.dtos.ticket.mapper.TicketMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static br.com.compass.ticketmanagement.common.EventConstants.EVENT;
import static br.com.compass.ticketmanagement.common.TicketConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(SpringExtension.class)
public class TicketMapperTest {

    @Test
    public void toEntity_ReturnsTicketDto(){
        Ticket sut = TicketMapper.toEntity(TICKET_REQUEST);

        assertThat(sut.getCpf()).isEqualTo(TICKET.getCpf());
        assertThat(sut.getCustomerMail()).isEqualTo(TICKET.getCustomerMail());
        assertThat(sut.getCustomerName()).isEqualTo(TICKET.getCustomerName());
        assertThat(sut.getEventId()).isEqualTo(TICKET.getEventId());
    }

    @Test
    public void toDto_ReturnsEntity(){
        TicketResponseDto sut = TicketMapper.toDto(TICKET, EVENT);

        assertThat(sut.getCpf()).isEqualTo(TICKET.getCpf());
        assertThat(sut.getCustomerMail()).isEqualTo(TICKET.getCustomerMail());
        assertThat(sut.getCustomerName()).isEqualTo(TICKET.getCustomerName());
        assertThat(sut.getEvent()).isEqualTo(TICKET_RESPONSE.getEvent());
    }
}
