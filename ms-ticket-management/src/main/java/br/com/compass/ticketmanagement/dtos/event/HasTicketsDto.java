package br.com.compass.ticketmanagement.dtos.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HasTicketsDto {
    @NonNull
    private String eventId;

    @NonNull
    private Boolean hasTickets;
    private String error = "The event cannot be deleted because tickets have been sold.";
}
