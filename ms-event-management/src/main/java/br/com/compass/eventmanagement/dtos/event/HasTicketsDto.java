package br.com.compass.eventmanagement.dtos.event;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class HasTicketsDto {
    @NonNull
    private String eventId;

    @NonNull
    private Boolean hasTickets;

    private String error;
}
