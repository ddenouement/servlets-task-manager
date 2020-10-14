package commands.impl.auth;

import commands.util.HttpAction;
import commands.ICommand;
import commands.util.ParameterException;
import commands.util.ParameterGetter;
import commands.util.PathUtils;
import dto.UserDTO;
import model.Role;
import model.User;
import service.ServiceException;
import service.UserService;
import sun.security.validator.ValidatorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Command to login
 *
 * @Author Yuliia Aleksandrova
 */
public class LoginCommand implements ICommand {
    private static final String LOGIN_PAGE_JSP = "loginPage.jsp";
    private static final String REDIRECT_ADMIN_STATS_PAGE = "/controller?command=stats";
    private static final String REDIRECT_USER_LIST_ACTIVITIES_PAGE = "/controller?command=activities";
    private static final String REDIRECT_LOGIN = "/controller?command=login";
    private static final String HOME_PAGE_JSP = "index.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) {

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
        String errorMessage;
        try {
            login = ParameterGetter.getStringParam(request, "login");
            password = ParameterGetter.getStringParam(request, "password");
        } catch (ParameterException e) {
            errorMessage = myBundle.getString("validation.no_password");
            request.getSession(true).setAttribute("errorMessage", errorMessage);
            return redirect;
        }

        //proceed with parameters given from request
        User user;
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

        session.setAttribute("user", user.getSimpleUserDTO());
        session.setAttribute("userRole", userRole);

        return redirect;
    }


    private String doGet(HttpServletRequest request,
                         HttpServletResponse response) {
        UserDTO userDTO = (UserDTO) request.getSession(false).getAttribute("user");
        if (userDTO == null) {
            PathUtils.saveCurrentPath(request, response);
            return LOGIN_PAGE_JSP;
        } else return HOME_PAGE_JSP;
    }
}
