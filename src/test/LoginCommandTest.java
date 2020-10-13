
import commands.impl.auth.LoginCommand;
import commands.util.HttpAction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.mockito.Mockito.*;


public class LoginCommandTest {
    @Mock
    HttpServletRequest mockedRequest;

    @Mock
    HttpServletResponse mockedResponse;

    @Mock
    HttpSession mockedSession;

    @Before
    public void setUp()  {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void givenPasswordAndLoginNull_whenLogin_shouldRedirectLogin() {
        when((mockedRequest.getParameter("user"))).thenReturn(null);
        when(mockedRequest.getParameter("password")).thenReturn(null);
        when(mockedRequest.getSession(true)).thenReturn(mockedSession);
        when(mockedRequest.getSession()).thenReturn(mockedSession);
        when(mockedSession.getAttribute("lang")).thenReturn(null);

        LoginCommand loginCommand = new LoginCommand();
        String res = loginCommand.execute(mockedRequest, mockedResponse, HttpAction.POST);
        Assert.assertEquals("/controller?command=login", res);
    }

    @Test
    public void givenPasswordAndLoginNull_whenLogin_shouldDisplayErrors() {
        Locale locale = Locale.ENGLISH;

        when((mockedRequest.getParameter("user"))).thenReturn(null);
        when(mockedRequest.getParameter("password")).thenReturn(null);
        when(mockedRequest.getSession(true)).thenReturn(mockedSession);
        when(mockedRequest.getSession()).thenReturn(mockedSession);
        when(mockedSession.getAttribute("lang")).thenReturn("en");

        LoginCommand loginCommand = new LoginCommand();
        String res = loginCommand.execute(mockedRequest, mockedResponse, HttpAction.POST);

        ResourceBundle myBundle = ResourceBundle.getBundle("lang", locale);

        verify(mockedSession, times(1)).setAttribute("errorMessage", myBundle.getString("validation.no_password"));
    }
}
