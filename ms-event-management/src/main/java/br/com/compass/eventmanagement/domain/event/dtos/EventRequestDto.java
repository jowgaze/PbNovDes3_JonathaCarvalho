package br.com.compass.eventmanagement.domain.event.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventRequestDto {
    @NotNull
    private String name;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Length(min = 8, max = 8)
    private String zipCode;
}
