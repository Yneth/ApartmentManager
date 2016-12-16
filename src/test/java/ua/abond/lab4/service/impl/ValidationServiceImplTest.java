package ua.abond.lab4.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.service.ValidationService;
import ua.abond.lab4.service.Validator;
import ua.abond.lab4.service.exception.NoSuchValidatorException;
import ua.abond.lab4.service.exception.ValidationException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ValidationServiceImplTest {
    private ValidationService service;

    @Before
    public void setUp() {
        service = new ValidationServiceImpl();
    }

    @Test(expected = NullPointerException.class)
    public void testRegisterNullClass() {
        service.register(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testRegisterNullValidator() {
        service.register(Object.class, null);
    }

    @Test(expected = NoSuchValidatorException.class)
    public void testNoSuchValidatorTryValidateMethod() {
        service.tryValidate(new Object());
    }

    @Test(expected = NoSuchValidatorException.class)
    public void testNoSuchValidatorValidateMethod() throws ValidationException {
        service.validate(new Object());
    }

    @Test(expected = NullPointerException.class)
    public void testValidateNull() throws ValidationException {
        service.validate(null);
    }

    @Test(expected = NullPointerException.class)
    public void testTryValidateNull() {
        service.tryValidate(null);
    }

    @Test
    public void testRegisterAndTryValidateShouldWorkFine() {
        Validator validator = mock(Validator.class);
        service.register(Integer.class, validator);
        Integer i = 1;
        service.tryValidate(i);
        verify(validator).validate(i);
    }

    @Test
    public void testRegisterAndValidateShouldWorkFine() throws ValidationException {
        Validator validator = mock(Validator.class);
        service.register(Integer.class, validator);
        Integer i = 1;
        service.validate(i);
        verify(validator).validate(i);
    }
}