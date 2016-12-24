package ua.abond.lab4.web.validation;

import org.junit.Test;
import ua.abond.lab4.web.dto.RequestDTO;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RequestDTOValidatorTest {

    @Test
    public void testSuccessfulValidation() {
        RequestDTO request = new RequestDTO();
        request.setFrom(LocalDate.now().plusDays(2));
        request.setTo(LocalDate.now().plusDays(10));
        request.setApartmentTypeId(0L);
        request.setRoomCount(10);
        List<String> validate = new RequestDTOValidator().validate(request);
        assertTrue(validate.isEmpty());
    }

    @Test
    public void testUnsuccessfulValidation() {
        RequestDTO request = new RequestDTO();
        List<String> validate = new RequestDTOValidator().validate(request);
        assertEquals(4, validate.size());
    }

    @Test
    public void testFromAfterToDate() {
        RequestDTO request = new RequestDTO();
        request.setFrom(LocalDate.now().plusDays(10));
        request.setTo(LocalDate.now());
        request.setApartmentTypeId(0L);
        request.setRoomCount(10);
        List<String> validate = new RequestDTOValidator().validate(request);
        assertEquals(1, validate.size());
    }

    @Test
    public void testFromIsToSoonOrInPast() {
        RequestDTO request = new RequestDTO();
        request.setFrom(LocalDate.now());
        request.setTo(LocalDate.now().plusDays(10));
        request.setApartmentTypeId(0L);
        request.setRoomCount(10);
        List<String> validate = new RequestDTOValidator().validate(request);
        assertEquals(1, validate.size());
    }
}