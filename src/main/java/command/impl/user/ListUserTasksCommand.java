package command.impl.user;

import command.util.HttpAction;
import command.ICommand;
import command.util.PathUtils;
import model.Motif;
import model.User;
import model.UserActivity;
import service.ActivityService;
import service.TaskService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ListUserTasksCommand implements ICommand {
    private static String VIEW_TASKS_OF_USER_JSP = "/user/task/listall.jsp";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) throws IOException, ServletException {

        PathUtils.saveCurrentPath(request, response);
        int user_id = 0;
        if(request.getParameter("user_id") == null){
            User user_in_session = (User) request.getSession().getAttribute("user");
            user_id = user_in_session.getId();
        }
        else {
            user_id = Integer.parseInt(request.getParameter("user_id"));
        }
        request.setAttribute("currentUserId", user_id);

        List<UserActivity> tasks = TaskService.getInstance().getTasksByUserId(user_id);
        request.setAttribute("tasks", tasks);

        String motif = Motif.REMOVE.getName();
        request.setAttribute("remove_motif_name", motif);

        return VIEW_TASKS_OF_USER_JSP;
    }
}
