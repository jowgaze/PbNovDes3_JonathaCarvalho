package br.com.compass.ticketmanagement.dtos.event;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AddressResponseDto {
    private String zipCode;
    private String street;
    private String neighborhood;
    private String locality;
    private String state;
}
