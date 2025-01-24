package br.com.compass.ticketmanagement.dtos.email;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmailDto {
    private String sender;
    private String recipient;
    private String subject;
    private String body;
}
