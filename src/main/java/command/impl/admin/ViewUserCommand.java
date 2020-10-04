package command.impl.admin;

import command.HttpAction;
import command.ICommand;
import command.PathUtils;
import model.Request;
import model.Role;
import model.User;
import service.RequestService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

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
        User currentUser  = (User)request.getSession().getAttribute("user");
        String paramId = request.getParameter("id");
        int userId = 0;
        try {
            userId =  Integer.parseInt(paramId);
        }
        catch (NumberFormatException a){
            return PathUtils.getSavedPath(request, response);
        }

        if(currentUser != null && currentUser.getRole() == Role.ADMIN) {
            PathUtils.saveCurrentPath(request, response);
            User user = UserService.getInstance().findUserById(userId);
            List<Request> requestList = RequestService.getInstance().getRequestsByUserId(userId);
            request.setAttribute("userview", user);
            request.setAttribute("userRequests", requestList);
            return USER_VIEW_PAGE_JSP;
        }
        request.getSession().setAttribute("errorMessage", "Access was denied");
        return PathUtils.getSavedPath(request, response);
    }
}
