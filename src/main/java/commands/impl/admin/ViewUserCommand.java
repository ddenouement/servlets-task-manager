package commands.impl.admin;

import commands.util.HttpAction;
import commands.ICommand;
import commands.util.ParameterException;
import commands.util.ParameterGetter;
import commands.util.PathUtils;
import dto.UserDTO;
import model.Request;
import model.Role;
import model.User;
import service.RequestService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
/**
 * Command to view info about user
 * @Author Yuliia Aleksandrova
 */
public class ViewUserCommand implements ICommand {

    private static final String USER_VIEW_PAGE_JSP = "admin/user/view.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) throws IOException, ServletException {
        String result = null;

        if (HttpAction.GET == action) {
            result = doGet(request, response);

        }
        return result;
    }

    private String doGet(HttpServletRequest request, HttpServletResponse response) {
        UserDTO currentUser = (UserDTO) request.getSession().getAttribute("user");
        int userId;
        try {
            userId = ParameterGetter.getIntParam(request, "id");
        }
        catch (ParameterException e) {
            return null;
        }

        if (currentUser != null && currentUser.getRole() == Role.ADMIN) {
                PathUtils.saveCurrentPath(request, response);
                Optional<User> user = UserService.getInstance().findUserById(userId);
                if (!user.isPresent()) {
                    return null;
                }
                List<Request> requestList = RequestService.getInstance().getRequestsByUserId(userId);
                request.setAttribute("userview", user.get().getSimpleUserDTO());
                request.setAttribute("userRequests", requestList);
                return USER_VIEW_PAGE_JSP;
            }
        return null;
    }
}
