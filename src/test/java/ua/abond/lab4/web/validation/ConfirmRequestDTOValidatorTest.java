package ua.abond.lab4.web.validation;

import org.junit.Test;
import ua.abond.lab4.web.dto.ConfirmRequestDTO;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ConfirmRequestDTOValidatorTest {

    @Test
    public void testValidationWithEmptyRequest() {
        ConfirmRequestDTO dto = new ConfirmRequestDTO();
        List<String> validate = new ConfirmRequestDTOValidator().validate(dto);
        assertNotNull(validate);
        assertEquals(4, validate.size());
    }

    @Test
    public void testSuccessfulValidation() {
        ConfirmRequestDTO dto = new ConfirmRequestDTO();
        dto.setApartmentId(1L);
        dto.setUserId(1L);
        dto.setRequestId(1L);
        dto.setPrice(new BigDecimal(100));
        List<String> validate = new ConfirmRequestDTOValidator().validate(dto);
        assertNotNull(validate);
        assertEquals(0, validate.size());
    }

    @Test
    public void testNegativePrice() {
        ConfirmRequestDTO dto = new ConfirmRequestDTO();
        dto.setPrice(new BigDecimal(-100));
        List<String> validate = new ConfirmRequestDTOValidator().validate(dto);
        assertNotNull(validate);
        assertEquals(1, validate.size());
    }
}