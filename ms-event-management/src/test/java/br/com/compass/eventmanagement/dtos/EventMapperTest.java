package br.com.compass.eventmanagement.dtos;

import br.com.compass.eventmanagement.dtos.event.EventResponseDto;
import br.com.compass.eventmanagement.dtos.event.mapper.EventMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static br.com.compass.eventmanagement.common.EventConstants.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(SpringExtension.class)
public class EventMapperTest {

    @Test
    public void toDto_ReturnsDto(){
        EventResponseDto sut = EventMapper.toDto(VALID_EVENT_SAVED);

        assertThat(sut.getId()).isEqualTo(VALID_EVENT_SAVED.getId());
        assertThat(sut.getName()).isEqualTo(VALID_EVENT_SAVED.getName());
        assertThat(sut.getDateTime()).isEqualTo(VALID_EVENT_SAVED.getDateTime());
        assertThat(sut.getAddress()).isEqualTo(VALID_EVENT_SAVED.getAddress());
    }

    @Test
    public void toListDto_ReturnsListDto(){
        List<EventResponseDto> sut = EventMapper.toListDto(EVENT_LIST);

        assertThat(sut.size()).isEqualTo(EVENT_LIST.size());
        assertThat(sut.get(0).getId()).isEqualTo(EVENT_LIST.get(0).getId());
        assertThat(sut.get(1).getId()).isEqualTo(EVENT_LIST.get(1).getId());
        assertThat(sut.get(2).getId()).isEqualTo(EVENT_LIST.get(2).getId());
    }
}
