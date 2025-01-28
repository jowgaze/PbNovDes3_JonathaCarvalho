package br.com.compass.eventmanagement.domain;

import br.com.compass.eventmanagement.domain.event.Event;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(SpringExtension.class)
public class EventTest {
    @Test
    public void eventEquals() {
        Event event1 = new Event();
        Event event2 = new Event();
        Event event3 = new Event();
        event1.setId("eventId1");
        event2.setId("eventId1");
        event3.setId("eventId3");

        assertEquals(event1, event2);
        assertNotEquals(event1, event3);
        assertNotEquals(new Object(), event1);
        assertNotEquals(null, event1);
        assertEquals(event1.hashCode(), event2.hashCode());
        assertNotEquals(event1.hashCode(), event3.hashCode());
    }
}
