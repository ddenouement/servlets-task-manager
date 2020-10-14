package commands.impl.admin;

import commands.ICommand;
import commands.util.HttpAction;
import commands.util.ParameterException;
import commands.util.ParameterGetter;

import commands.util.PathUtils;
import model.Role;
import service.ActivityService;
import service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command to delete existing activity
 * @Author Yuliia Aleksandrova
 */
public class DeleteActivityCommand implements ICommand {

     private static final String REDIRECT_ACTIVITIES_LIST_PAGE = "controller?command=activities";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) {
        String result = null;
         if (action == HttpAction.POST) {
            result = doPost(request, response);
        }
        return result;
    }

    private String doPost(HttpServletRequest request, HttpServletResponse response) {

        String role =  (String) request.getSession().getAttribute("userRole");
        if(role==null||Role.valueOf(role.toUpperCase())!= Role.ADMIN){
            request.getSession().setAttribute("errorMessage", "Access denied");
            return REDIRECT_ACTIVITIES_LIST_PAGE;
        }

        int activityId =0;
        try {
              activityId = ParameterGetter.getIntParam(request, "id");
        } catch (ParameterException e) {
            return REDIRECT_ACTIVITIES_LIST_PAGE;
        }

        try {
            ActivityService.getInstance().deleteActivity(activityId);
            request.getSession().setAttribute("infoMessage", "You  deleted successfully");

        } catch (ServiceException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
        }

        return REDIRECT_ACTIVITIES_LIST_PAGE;
    }

}