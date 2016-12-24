package ua.abond.lab4.web.validation;

import org.junit.Test;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.ApartmentType;
import ua.abond.lab4.domain.Request;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RequestValidatorTest {

    @Test
    public void testEmptyRequest() {
        Request request = new Request();
        List<String> validate = new RequestValidator().validate(request);
        assertEquals(3, validate.size());
    }

    @Test
    public void testRoomCountLessThanZero() {
        Request request = new Request();
        request.setFrom(LocalDate.now());
        request.setTo(LocalDate.now().plusDays(10));

        Apartment apartment = new Apartment();
        apartment.setRoomCount(-10);

        ApartmentType apartmentType = new ApartmentType();
        apartmentType.setId(1L);
        apartment.setType(apartmentType);

        request.setLookup(apartment);
        List<String> validate = new RequestValidator().validate(request);
        assertEquals(1, validate.size());
    }

    @Test
    public void testRoomCountGreaterThenMax() {
        Request request = new Request();
        request.setFrom(LocalDate.now());
        request.setTo(LocalDate.now().plusDays(10));

        Apartment apartment = new Apartment();
        apartment.setRoomCount(100);

        ApartmentType apartmentType = new ApartmentType();
        apartmentType.setId(1L);
        apartment.setType(apartmentType);

        request.setLookup(apartment);
        List<String> validate = new RequestValidator().validate(request);
        assertEquals(1, validate.size());
    }

    @Test
    public void testSuccessfulValidation() {
        Request request = new Request();
        request.setFrom(LocalDate.now());
        request.setTo(LocalDate.now().plusDays(10));

        Apartment apartment = new Apartment();
        apartment.setRoomCount(10);

        ApartmentType apartmentType = new ApartmentType();
        apartmentType.setId(1L);
        apartment.setType(apartmentType);

        request.setLookup(apartment);

        List<String> validate = new RequestValidator().validate(request);
        assertTrue(validate.isEmpty());
    }
}