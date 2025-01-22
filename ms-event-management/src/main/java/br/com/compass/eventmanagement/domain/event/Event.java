package br.com.compass.eventmanagement.domain.event;

import br.com.compass.eventmanagement.domain.address.Address;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collection = "event")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Event implements Serializable {
    @Id
    private String id;

    @NonNull
    private String name;

    @NonNull
    private LocalDateTime date;

    @DBRef(lazy = true)
    @NonNull
    private Address address;
}
