package br.com.compass.eventmanagement.domain.event;

import br.com.compass.eventmanagement.domain.address.Address;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Document(collection = "event")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Event implements Serializable {
    @Id
    private String id;

    @NonNull
    private String name;

    @NonNull
    private LocalDateTime dateTime;

    @DBRef(lazy = true)
    @NonNull
    private Address address;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
