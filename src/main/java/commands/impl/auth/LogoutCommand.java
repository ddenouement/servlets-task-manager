package commands.impl.auth;

import commands.util.HttpAction;
import commands.ICommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * Command to logout
 * @Author Yuliia Aleksandrova
 */
public class LogoutCommand implements ICommand {
    private static final String REDIRECT_MAIN_PAGE = "";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) throws IOException, ServletException {

        String result = null;
        if (HttpAction.POST == action) {
            result = doPost(request, response);
        }
        return result;
    }

    private String doPost(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();

        return REDIRECT_MAIN_PAGE;
    }

}
