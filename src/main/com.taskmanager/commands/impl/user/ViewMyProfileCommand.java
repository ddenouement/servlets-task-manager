package commands.impl.user;

import commands.util.HttpAction;
import commands.ICommand;
import commands.util.PathUtils;
import model.Request;
import model.User;
import service.RequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
