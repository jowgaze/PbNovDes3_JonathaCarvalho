package br.com.compass.eventmanagement.domain.address;

import br.com.compass.eventmanagement.services.client.viacep.ViaCepResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "address")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address implements Serializable {
    @Id
    private String zipCode;
    private String street;
    private String neighborhood;
    private String locality;
    private String state;

    public Address(ViaCepResponse viaCepResponse){
        zipCode = viaCepResponse.getCep();
        street = viaCepResponse.getLogradouro();
        neighborhood = viaCepResponse.getBairro();
        locality = viaCepResponse.getLocalidade();
        state = viaCepResponse.getUf();
    }
}
