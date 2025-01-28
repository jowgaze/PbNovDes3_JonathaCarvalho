package br.com.compass.eventmanagement.domain;

import br.com.compass.eventmanagement.domain.address.Address;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(SpringExtension.class)
public class AddressTest {
    @Test
    public void addressEquals() {
        Address address1 = new Address();
        Address address2 = new Address();
        Address address3 = new Address();
        address1.setZipCode("01001000");
        address2.setZipCode("01001000");
        address3.setZipCode("01001001");

        assertEquals(address1, address2);
        assertNotEquals(address1, address3);
        assertNotEquals(new Object(), address1);
        assertNotEquals(null, address1);
        assertEquals(address1.hashCode(), address2.hashCode());
        assertNotEquals(address1.hashCode(), address3.hashCode());
    }
}
