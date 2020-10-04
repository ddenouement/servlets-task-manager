package command.impl.admin;

import com.mysql.cj.Session;
import command.HttpAction;
import command.ICommand;
import command.PathUtils;
import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListUsersCommand implements ICommand {


    private static final String USERS_LIST_PAGE_JSP = "admin/user/listall.jsp";

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

        List<User> users = UserService.getInstance().findAllUsers();
        request.setAttribute("users", users);
        return USERS_LIST_PAGE_JSP;
    }
}