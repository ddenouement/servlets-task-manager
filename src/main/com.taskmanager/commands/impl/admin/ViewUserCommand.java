package commands.impl.admin;

import commands.util.HttpAction;
import commands.ICommand;
import commands.util.PathUtils;
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
        User currentUser = (User) request.getSession().getAttribute("user");
        String paramId = request.getParameter("id");
        int userId ;
        try {
            userId = Integer.parseInt(paramId);
        } catch (NumberFormatException a) {
            return PathUtils.getSavedPath(request, response);
        }

        if (currentUser != null && currentUser.getRole() == Role.ADMIN) {
            PathUtils.saveCurrentPath(request, response);
            Optional<User> user = UserService.getInstance().findUserById(userId);
            List<Request> requestList = null;
            if (user.isPresent()) {
                requestList = RequestService.getInstance().getRequestsByUserId(userId);
            }
            if (!user.isPresent()) {
                request.setAttribute("errorMessage", "No such user found");
            }
            request.setAttribute("userview", user.get());
            request.setAttribute("userRequests", requestList);
            return USER_VIEW_PAGE_JSP;
        }


        request.setAttribute("errorMessage", "Access was denied");
        return PathUtils.getSavedPath(request, response);
    }
}
