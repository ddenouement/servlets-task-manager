package command.impl.auth;

import command.util.HttpAction;
import command.ICommand;
import command.util.PathUtils;
import model.Role;
import model.User;
import service.ServiceException;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class LoginCommand implements ICommand {
    private static final String LOGIN_PAGE_JSP = "loginPage.jsp";
    private static final String REDIRECT_ADMIN_STATS_PAGE = "/controller?command=stats";
    private static final String REDIRECT_USER_LIST_ACTIVITIES_PAGE = "/controller?command=activities";
    private static final String REDIRECT_LOGIN = "/controller?command=login";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action)  {

        String result = null;
        if (HttpAction.GET == action) {
            result = doGet(request, response);
        } else if (HttpAction.POST == action) {
            result = doPost(request, response);
        }
        return result;
    }

    private String doPost(HttpServletRequest request, HttpServletResponse response) {

        String redirect = REDIRECT_LOGIN;
        HttpSession session = request.getSession(true);

        // obtain login and password from the request
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String errorMessage ;

        User user ;
        try {
            user = UserService.getInstance().login(login, password);
        } catch (ServiceException e) {
            errorMessage = "Cannot find user with such login";
            request.getSession(true).setAttribute("errorMessage", errorMessage);
            return redirect;
        }
        String userRole = user.getRole().getName();

        if (Role.ADMIN.getName().equals(userRole))
            redirect = REDIRECT_ADMIN_STATS_PAGE;

        if (Role.USER.getName().equals(userRole))
            redirect = REDIRECT_USER_LIST_ACTIVITIES_PAGE;

        session.setAttribute("user", user);
        session.setAttribute("userRole", userRole);

        return redirect;
    }


    private String doGet(HttpServletRequest request,
                         HttpServletResponse response) {
        PathUtils.saveCurrentPath(request, response);
        return LOGIN_PAGE_JSP;
    }
}
