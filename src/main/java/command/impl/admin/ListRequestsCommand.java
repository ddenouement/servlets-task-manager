package command.impl.admin;

import command.HttpAction;
import command.ICommand;
import command.PathUtils;
import model.Request;
import model.User;
import service.RequestService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ListRequestsCommand implements ICommand {

    private static final String REQUESTS_LIST_PAGE_JSP = "admin/request/listall.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) throws IOException, ServletException {
        String result = null;

        if (HttpAction.GET == action) {
            result = doGet(request, response);

        }
        return result;
    }

    private String doGet(HttpServletRequest request, HttpServletResponse response) {
        PathUtils.saveCurrentPath(request,response);


        List<Request> requests = RequestService.getInstance().getAllRequests();
        HttpSession session = request.getSession(false);
        session.setAttribute("requests", requests);
        return REQUESTS_LIST_PAGE_JSP;
    }
}
