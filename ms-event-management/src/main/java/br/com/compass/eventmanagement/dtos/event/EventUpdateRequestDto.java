package br.com.compass.eventmanagement.dtos.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EventUpdateRequestDto {
    private String name;

    @Schema(example = "2025-11-13T20:00:00Z")
    private LocalDateTime dateTime;

    @Length(min = 8, max = 8)
    private String zipCode;
}
