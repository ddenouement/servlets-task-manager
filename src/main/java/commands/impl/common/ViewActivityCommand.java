package commands.impl.common;

import commands.util.HttpAction;
import commands.ICommand;
import commands.util.PathUtils;
import dto.UserDTO;
import model.*;
import service.ActivityService;
import service.TaskService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
/**
 * Command to view info about selected activity
 * @Author Yuliia Aleksandrova
 */
public class ViewActivityCommand implements ICommand {

    private static final String JSP_VIEW_ACTIVITY_PAGE ="viewActivityPage.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) {
        String result = null;
        if(action == HttpAction.GET){
           result = doGet(request, response);
        }
        return  result;
    }

    private String doGet(HttpServletRequest request, HttpServletResponse response) {

        PathUtils.saveCurrentPath(request, response);

        UserDTO user_in_session = (UserDTO) request.getSession().getAttribute("user");
        if (user_in_session == null){
            return null;
        }

        int activityId = Integer.parseInt(request.getParameter("id"));
        Optional<Activity> activity = ActivityService.getInstance()
                .getActivityById(activityId);
        if(!activity.isPresent())
            request.setAttribute("errorMessage", "No activity found");

        request.setAttribute("activity", activity.get());

        String filterProgress = request.getParameter("progress");
        String progress = Progress.ASSIGNED.getName();
        if(filterProgress !=null && !filterProgress.isEmpty()){
              progress = filterProgress;
        }
        List<UserActivity> activityTasks = TaskService.getInstance().getTasksByActivityByProgress(activityId, progress);
        request.setAttribute("tasks", activityTasks);

        request.setAttribute("progresses", Progress.values());

        request.setAttribute("filterByProgress", progress);

        return JSP_VIEW_ACTIVITY_PAGE;
    }
}
