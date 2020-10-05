package command.impl.admin;

import command.util.HttpAction;
import command.ICommand;
import command.util.PathUtils;
import service.ActivityService;
import service.RequestService;
import service.TaskService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StatsCommand implements ICommand {
    private static String STATS_VIEW_JSP = "/admin/stats/view.jsp";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) throws IOException, ServletException {
       if( action == HttpAction.GET){
           return doGet(request, response);
       }
       return null;
    }

    private String doGet(HttpServletRequest request, HttpServletResponse response) {

        PathUtils.saveCurrentPath(request,response);
        int countOfRequestsWaiting = RequestService.getInstance().getNumberOfCreatedRequests();
        int countOfUsers = UserService.getInstance().getCountOfUsers();
        int countOfActivities = ActivityService.getInstance().getCountOfActivities();

        request.setAttribute("numUsers", countOfUsers);
        request.setAttribute("numActivities", countOfActivities);
        request.setAttribute("numRequestsWaiting", countOfRequestsWaiting);
        return STATS_VIEW_JSP;
    }
}
