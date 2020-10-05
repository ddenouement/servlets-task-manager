package command.impl.user;

import command.util.HttpAction;
import model.User;
import service.ServiceException;
import service.TaskService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class FinishMyTaskCommand implements command.ICommand {
   private static final String  REDIRECT_ALL_MY_TASKS = "controller?command=tasks";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) throws IOException, ServletException {
        if (action == HttpAction.POST){
            return doPost(request, response);
        }
        return null;
    }

    private String doPost(HttpServletRequest request, HttpServletResponse response) {
              int taskId = Integer.parseInt(request.getParameter("id"));
            int hoursSpent = Integer.parseInt(request.getParameter("hours"));
            User user  = (User) request.getSession().getAttribute("user");
            if(user !=null ){
                try {
                    TaskService.getInstance().finishTask(taskId, hoursSpent);
                    request.getSession().setAttribute("infoMessage", "You finished this task successfully");
                } catch (ServiceException e) {
                    request.getSession().setAttribute("errorMessage", e.getMessage());
                }
            }
            return REDIRECT_ALL_MY_TASKS;
    }
}
