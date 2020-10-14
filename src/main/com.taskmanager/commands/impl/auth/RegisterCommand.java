package commands.impl.auth;

import commands.util.HttpAction;
import commands.ICommand;
import commands.util.PathUtils;
import dto.UserDTO;
import model.User;
import service.ServiceException;
import service.UserService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Command to register as a new user
 * @Author Yuliia Aleksandrova
 */
public class RegisterCommand implements ICommand {

    private static final String REDIRECT_REGISTER_PAGE = "controller?command=register";
    private static final String REDIRECT_LOGIN_PAGE = "controller?command=login";
    private static final String REGISTER_PAGE_JSP = "registerPage.jsp";
    private static final String LOGOUT_PAGE_JSP = "logout.jsp";
    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response, HttpAction actionType) {

        String result = null;

        if (HttpAction.GET == actionType) {
            result = doGet(request, response);
        } else if (HttpAction.POST == actionType) {
            result = doPost(request, response);
        }
        return result;
    }

    private String doPost(HttpServletRequest request, HttpServletResponse response) {

        //obtain parameters from request
        String login = request.getParameter("login");
        String name = request.getParameter("name");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String errorMessage;

        //obtain locale from session or default
        String language = (String) request.getSession().getAttribute("lang");
        Locale locale = (language == null) ? Locale.getDefault() : new Locale(language);
        ResourceBundle myBundle = ResourceBundle.getBundle("lang", locale);

        //if we found user with such login, return error message and redirect to login page again
        Optional<User> foundUser = UserService.getInstance().findUserByLogin(login);
        if (foundUser.isPresent()) {
            errorMessage = myBundle.getString("exception.login_exists");
            request.getSession(true).setAttribute("errorMessage", errorMessage);
            return REDIRECT_REGISTER_PAGE;
        } else {
            //create user object
            User user = new User.Builder()
                    .withRole(role)
                    .withEmail(email)
                    .withFirstname(name)
                    .withLastname(lastname)
                    .withLogin(login)
                    .withPassword(password)
                    .build();

            //Validate created User object, before going to Database
            Set<String> validationMessages = user.validate();
            String validationMessage =
                    validationMessages.stream()
                    .map(myBundle::getString)
                    .collect(Collectors.joining(","));

            //only if there are no validation exceptions
            if (validationMessage.isEmpty()) {
                //Try to and add it to database
                try {
                    UserService.getInstance().register(user);
                    request.getSession(true).setAttribute("infoMessage", myBundle.getString("message.register_success"));

                } catch (ServiceException e) {
                    errorMessage = myBundle.getString("exception.login_exists");
                    request.getSession(true).setAttribute("errorMessage", errorMessage);
                    return REDIRECT_REGISTER_PAGE;
                }
            }

            else {
                request.getSession(true).setAttribute("errorMessage", validationMessage);
                return REDIRECT_REGISTER_PAGE;
            }
        }
        return REDIRECT_LOGIN_PAGE;
    }


    private String doGet(HttpServletRequest request,
                         HttpServletResponse response) {
        UserDTO userDTO = (UserDTO) request.getSession(false).getAttribute("user");
       if(userDTO==null) {
           PathUtils.saveCurrentPath(request, response);
           return REGISTER_PAGE_JSP;
       }
       else return LOGOUT_PAGE_JSP;
    }
}
