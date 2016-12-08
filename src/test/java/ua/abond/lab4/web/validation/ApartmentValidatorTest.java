package ua.abond.lab4.web.validation;

import org.junit.Test;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.ApartmentType;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApartmentValidatorTest {

    @Test
    public void testValidationOfEmptyApartment() {
        Apartment apartment = new Apartment();
        List<String> validate = new ApartmentValidator().validate(apartment);
        assertEquals(4, validate.size());
    }

    @Test
    public void testSuccessfulValidation() {
        Apartment apartment = createSuccessful();
        List<String> validate = new ApartmentValidator().validate(apartment);
        assertTrue(validate.isEmpty());
    }

    @Test
    public void testBadPrice() {
        Apartment apartment = createSuccessful();
        apartment.setPrice(new BigDecimal(-1));
        List<String> validate = new ApartmentValidator().validate(apartment);
        assertEquals(1, validate.size());
    }

    @Test
    public void testZeroRoomCount() {
        Apartment apartment = createSuccessful();
        apartment.setRoomCount(0);
        List<String> validate = new ApartmentValidator().validate(apartment);
        assertEquals(1, validate.size());
    }

    @Test
    public void testTooBigRoomCount() {
        Apartment apartment = createSuccessful();
        apartment.setRoomCount(12);
        List<String> validate = new ApartmentValidator().validate(apartment);
        assertEquals(1, validate.size());
    }

    @Test
    public void testEmptyName() {
        Apartment apartment = createSuccessful();
        apartment.setName("");
        List<String> validate = new ApartmentValidator().validate(apartment);
        assertEquals(1, validate.size());
    }

    private Apartment createSuccessful() {
        Apartment apartment = new Apartment();
        apartment.setName("name");
        apartment.setPrice(new BigDecimal(100));
        ApartmentType apartmentType = new ApartmentType();
        apartmentType.setId(1L);
        apartment.setType(apartmentType);
        apartment.setRoomCount(10);
        return apartment;
    }
}