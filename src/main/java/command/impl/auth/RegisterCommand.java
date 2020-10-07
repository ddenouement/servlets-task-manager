package command.impl.auth;

import command.util.HttpAction;
import command.ICommand;
import command.util.PathUtils;
import model.User;
import service.ServiceException;
import service.UserService;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class RegisterCommand implements ICommand {

    private static final String REDIRECT_REGISTER_PAGE = "controller?command=register";
    private static final String REDIRECT_LOGIN_PAGE = "controller?command=login";
    private static final String REGISTER_PAGE_JSP = "registerPage.jsp";

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response, HttpAction actionType)
        {

        String result = null;

        if (HttpAction.GET == actionType) {
            result = doGet(request, response);
        } else if (HttpAction.POST == actionType) {
            result = doPost(request, response);
        }
        return result;
    }

    private String doPost(HttpServletRequest request, HttpServletResponse response) {

       String login = request.getParameter("login");
        String name = request.getParameter("name");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String errorMessage;
        Optional<User> foundUser = UserService.getInstance().findUserByLogin(login);

        if (foundUser.isPresent()) {
            errorMessage = "Login is already in use!";
            request.getSession(true).setAttribute("errorMessage", errorMessage);
            return REDIRECT_REGISTER_PAGE;
        } else {
            User user = new User();
            user.setRole(role);
            user.setEmail(email);
            user.setFirstName(name);
            user.setPassword(password);
            user.setLastName(lastname);
            user.setLogin(login);
            try {
                UserService.getInstance().register(user);
                request.getSession(true).setAttribute("infoMessage", "You registered successfully, now login");
            } catch (ServiceException e) {
                errorMessage = "Login is already in use!";
                request.getSession(true).setAttribute("errorMessage", errorMessage);
                return REDIRECT_REGISTER_PAGE;
            }

        }
        return REDIRECT_LOGIN_PAGE;
    }


    private String doGet(HttpServletRequest request,
                         HttpServletResponse response) {
        PathUtils.saveCurrentPath(request, response);
        return REGISTER_PAGE_JSP;
    }
}
