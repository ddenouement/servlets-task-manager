package command.impl.auth;

import command.util.HttpAction;
import command.ICommand;
import command.util.ParameterGetter;
import command.util.PathUtils;
import model.Role;
import model.User;
import service.ServiceException;
import service.UserService;
import sun.security.validator.ValidatorException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

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

        //obtain locale from session or default
        String language = (String) request.getSession().getAttribute("lang");
        Locale locale = (language == null) ? Locale.getDefault() : new Locale(language);
        ResourceBundle myBundle = ResourceBundle.getBundle("lang", locale);

        //try obtain login and password from the request using custom helper class
        String login = null;
        String password = null;
        String errorMessage ;
        try {
            login = ParameterGetter.getStringParam(request,"login");
            password =  ParameterGetter.getStringParam(request, "password");
        } catch (ValidatorException e) {
            errorMessage = myBundle.getString("validation.no_password");
            request.getSession(true).setAttribute("errorMessage", errorMessage);
            return redirect;
        }

        //proceed with parameters given from request
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
