package command.impl.user;

import command.util.HttpAction;
import command.ICommand;
import command.util.PathUtils;
import model.Request;
import model.User;
import model.UserActivity;
import service.RequestService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ViewMyProfileCommand implements ICommand {

    private final static String MY_PROFILE_VIEW_PAGE_JSP = "/user/profile/viewMyProfile.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) {
        String result = null;

        if (HttpAction.GET == action) {
            result = doGet(request, response);

        }
        return result;
    }

    private String doGet(HttpServletRequest request, HttpServletResponse response) {
        PathUtils.saveCurrentPath(request, response);
        User user = (User) request.getSession(false).getAttribute("user");
        if (user != null) {
            int userId = user.getId();
            List<Request> requestList = RequestService.getInstance().getRequestsByUserId(userId);
            request.setAttribute("myRequests", requestList);

            return MY_PROFILE_VIEW_PAGE_JSP;
        }
        return null;
    }
}
