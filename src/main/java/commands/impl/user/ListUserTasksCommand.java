package commands.impl.user;

import commands.util.HttpAction;
import commands.ICommand;
import commands.util.PathUtils;
import dto.UserDTO;
import model.Motif;
import model.Role;
import model.User;
import model.UserActivity;
import service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Command to view list of tasks of a user
 *
 * @Author Yuliia Aleksandrova
 */
public class ListUserTasksCommand implements ICommand {
    private static String VIEW_TASKS_OF_USER_JSP = "/user/task/listall.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) throws IOException, ServletException {
        PathUtils.saveCurrentPath(request, response);
        int userIdToWatch = 0;
        if (request.getParameter("user_id") == null) {
            UserDTO user_in_session = (UserDTO) request.getSession().getAttribute("user");
            if (user_in_session == null) {
                return null;
            }
            userIdToWatch = user_in_session.getId();
        } else {
            userIdToWatch = Integer.parseInt(request.getParameter("user_id"));
            String role = (String) request.getSession().getAttribute("userRole");
            if (role == null || Role.valueOf(role.toUpperCase()) != Role.ADMIN) {
                return null;
            }
        }

        request.setAttribute("currentUserId", userIdToWatch);
        List<UserActivity> tasks = TaskService.getInstance().getTasksByUserId(userIdToWatch);
        request.setAttribute("tasks", tasks);
        String motif = Motif.REMOVE.getName();
        request.setAttribute("remove_motif_name", motif);
        return VIEW_TASKS_OF_USER_JSP;
    }
}
