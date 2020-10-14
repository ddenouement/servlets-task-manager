package commands.impl.admin;

import commands.util.HttpAction;
import commands.ICommand;
import commands.util.PathUtils;
import dto.UserDTO;
import model.Role;
import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Command to view all users with role USER
 * @Author Yuliia Aleksandrova
 */
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
        String role =  (String) request.getSession().getAttribute("userRole");
        if(role==null||Role.valueOf(role.toUpperCase())!= Role.ADMIN){
           return null;
        }

        PathUtils.saveCurrentPath(request,response);

        List<UserDTO> users = UserService.getInstance().findAllUsers()
                .stream()
                .map(User::getSimpleUserDTO)
                .collect(Collectors.toList());
        request.setAttribute("users", users);
        return USERS_LIST_PAGE_JSP;
    }
}