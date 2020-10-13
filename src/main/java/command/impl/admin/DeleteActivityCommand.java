package command.impl.admin;

import command.ICommand;
import command.util.HttpAction;
import command.util.ParameterGetter;
import command.util.PathUtils;
import model.Activity;
import model.Category;
import service.ActivityService;
import service.ServiceException;
import sun.security.validator.ValidatorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        int activityId =0;
        try {
              activityId = ParameterGetter.getIntParam(request, "id");
        } catch (ValidatorException e) {
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