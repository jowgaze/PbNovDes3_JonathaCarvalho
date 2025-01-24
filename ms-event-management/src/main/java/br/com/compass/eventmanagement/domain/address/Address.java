package br.com.compass.eventmanagement.domain.address;

import br.com.compass.eventmanagement.dtos.viacep.ViaCepResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

@Document(collection = "address")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties({"target", "source"})
public class Address implements Serializable {
    @Id
    private String zipCode;
    private String street;
    private String neighborhood;
    private String locality;
    private String state;

    public Address(ViaCepResponseDto viaCepResponse){
        zipCode = viaCepResponse.getCep();
        street = viaCepResponse.getLogradouro();
        neighborhood = viaCepResponse.getBairro();
        locality = viaCepResponse.getLocalidade();
        state = viaCepResponse.getUf();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(zipCode, address.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(zipCode);
    }
}
