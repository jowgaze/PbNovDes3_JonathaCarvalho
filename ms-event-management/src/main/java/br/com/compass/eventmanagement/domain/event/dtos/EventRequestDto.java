package br.com.compass.eventmanagement.domain.event.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateTime;

    @NotNull
    @Length(min = 8, max = 8)
    private String zipCode;
}
