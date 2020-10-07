package command.impl.admin;

import command.util.HttpAction;
import command.ICommand;
import command.util.PathUtils;
import dao.UserActivityRepository;
import model.UserActivity;
import service.ActivityService;
import service.RequestService;
import service.TaskService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class StatsCommand implements ICommand {
    private static String STATS_VIEW_JSP = "/admin/stats/view.jsp";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action)  {
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
        Map<String, Double> averages =
                TaskService.getInstance().getAllTasksStream()
                        .collect( Collectors.groupingBy(a -> a.getActivity().getName(),
                                Collectors.averagingDouble(UserActivity::getTimeSpentInHours)));

        request.setAttribute("numUsers", countOfUsers);
        request.setAttribute("numActivities", countOfActivities);
        request.setAttribute("numRequestsWaiting", countOfRequestsWaiting);
        request.setAttribute("mapOfAverageTimes", averages);

        return STATS_VIEW_JSP;
    }
}
