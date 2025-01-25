package br.com.compass.eventmanagement.dtos.event;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EventRequestDto {
    @NotNull
    private String name;

    @NotNull
    @Schema(example = "2025-11-13T20:00:00Z")
    private LocalDateTime dateTime;

    @NotNull
    @Length(min = 8, max = 8)
    private String zipCode;
}
