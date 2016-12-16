package ua.abond.lab4.web.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.web.dto.ChangePasswordDTO;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ChangePasswordDTORequestMapperTest {
    @Mock
    private HttpServletRequest request;
    private ChangePasswordDTORequestMapper mapper;

    @Before
    public void setUp() {
        mapper = new ChangePasswordDTORequestMapper();
    }

    @Test
    public void testEmptyRequest() {
        ChangePasswordDTO map = mapper.map(request);
        assertNull(map.getNewPassword());
        assertNull(map.getNewPasswordCopy());
        assertNull(map.getOldPassword());
    }

    @Test
    public void testMap() {
        String password = "newPassword";
        when(request.getParameter("oldPassword")).thenReturn(password);
        when(request.getParameter("newPassword")).thenReturn(password);
        when(request.getParameter("newPasswordCopy")).thenReturn(password);

        ChangePasswordDTO map = mapper.map(request);
        assertEquals(map.getOldPassword(), map.getOldPassword());
        assertEquals(map.getNewPassword(), map.getNewPassword());
        assertEquals(map.getNewPasswordCopy(), map.getNewPasswordCopy());
    }
}