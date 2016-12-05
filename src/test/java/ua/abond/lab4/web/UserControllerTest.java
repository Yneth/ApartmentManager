package ua.abond.lab4.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.config.core.web.support.*;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.RequestService;
import ua.abond.lab4.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession httpSession;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private UserService userService;
    @Mock
    private RequestService requestService;
    @InjectMocks
    private UserController userController;
    @Spy
    private Page<Request> page = new DefaultPage<>(new ArrayList<>(), 0, null);
    private Pageable pageable = new DefaultPageable(1, 10, SortOrder.ASC);

    @Before
    public void setUp() {
        page = new DefaultPage<>(new ArrayList<>(), 0, null);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testViewRequests() throws Exception {
        User user = create("login", "password");
        user.setId(1L);
        mockUserToSession(user);
        mockPageableToRequest();
        when(requestService.getUserRequests(any(Pageable.class), anyLong())).
                thenReturn(mock(Page.class));
        userController.viewRequests(request, response);

        verify(requestService).getUserRequests(any(Pageable.class), any(Long.class));
    }

    @Test
    public void viewRequest() throws Exception {

    }

    @Test
    public void getCreateRequestPage() throws Exception {

    }

    @Test
    public void createRequest() throws Exception {

    }

    @Test
    public void rejectRequest() throws Exception {

    }

    @Test
    public void viewOrders() throws Exception {

    }

    @Test
    public void viewOrder() throws Exception {

    }

    @Test
    public void payOrder() throws Exception {

    }

    private void mockUserToSession(User user) {
        when(request.getSession(anyBoolean())).thenReturn(httpSession);
        when(httpSession.getAttribute("user")).thenReturn(user);
    }

    private void mockPageableToRequest() {
        when(request.getParameter("page")).thenReturn(pageable.getPageNumber() + "");
        when(request.getParameter("pageSize")).thenReturn(pageable.getPageSize() + "");
        when(request.getParameter("order")).thenReturn(pageable.getSortOrder().toString());
    }

    private User create(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return user;
    }
}