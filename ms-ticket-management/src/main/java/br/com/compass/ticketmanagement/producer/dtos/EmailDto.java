package br.com.compass.ticketmanagement.producer.dtos;

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
