package command.impl;

import command.HttpAction;
import command.ICommand;
import command.PathUtils;
import model.*;
import service.ActivityService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ViewActivityCommand implements ICommand {

    private static final String JSP_VIEW_ACTIVITY_PAGE ="viewActivityPage.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) throws IOException, ServletException {
        String result = null;
        if(action == HttpAction.GET){
           result = doGet(request, response);
        }
        return  result;
    }

    private String doGet(HttpServletRequest request, HttpServletResponse response) {

        PathUtils.saveCurrentPath(request, response);

        int activityId = Integer.parseInt(request.getParameter("id"));
        Activity activity = ActivityService.getInstance().getActivityById(activityId);
        request.setAttribute("activity", activity);

        String filterProgress = request.getParameter("progress");
        String progress = Progress.ASSIGNED.getName();
        if(filterProgress !=null && !filterProgress.isEmpty()){
              progress = filterProgress;
        }
        List<UserActivity> activityTasks = UserService.getInstance().getTasksByActivityByProgress(activityId, progress);
        request.setAttribute("tasks", activityTasks);

        //List<Progress> progressList = ProgressService.getInstance().getAllProgress();
        request.setAttribute("progresses", Progress.values());

        request.setAttribute("filterByProgress", progress);

        return JSP_VIEW_ACTIVITY_PAGE;
    }
}
