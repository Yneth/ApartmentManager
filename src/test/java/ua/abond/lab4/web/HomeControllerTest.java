package ua.abond.lab4.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest extends ControllerTest {

    @Test
    public void testGetIndexPage() throws Exception {
        new HomeController().getIndexPage(request, response);

        verify(request).getRequestDispatcher(HomeController.HOME_VIEW);
        verifyForward();
    }
}