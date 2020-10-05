package command.impl.admin;

import command.util.HttpAction;
import command.ICommand;
import command.util.PathUtils;
import model.Activity;
import model.Category;
import model.Progress;
import model.UserActivity;
import service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class EditActivityCommand implements ICommand {
    private static String REDIRECT_ON_VIEW_ACTIVITY_PAGE = "controller?command=viewActivity";

    private static String FORWARD_JSP_EDIT_ACTIVITY_PAGE = "/admin/activity/edit.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) throws IOException, ServletException {
        String result = null;
        if (action == HttpAction.GET) {
            result = doGet(request, response);
        } else if (action == HttpAction.POST) {
            result = doPost(request, response);
        }
        return result;
    }

    private String doPost(HttpServletRequest request, HttpServletResponse response) {

        String nameParam = request.getParameter("name");
        String nameEnParam = request.getParameter("nameEn");
        String nameUaParam = request.getParameter("nameUa");
        String categoryParam = request.getParameter("category");
        String descrParamEn = request.getParameter("descriptionEn");
        String descrParamUa = request.getParameter("descriptionUa");
        String descrParam = request.getParameter("description");
        String activityIdParam = request.getParameter("id");
        int activityId = Integer.parseInt(activityIdParam);

        Activity activity = new Activity();
        activity.setId(activityId);
        activity.setDescription(descrParam);
        activity.setDescriptionUa(descrParamUa);
        activity.setDescriptionEn(descrParamEn);
        activity.setName(nameParam);
        activity.setCategory(categoryParam);
        activity.setNameUa(nameUaParam);
        activity.setNameEn(nameEnParam);
        try {
            ActivityService.getInstance().checkAndUpdateActivity(activity);
            request.getSession().setAttribute("infoMessage", "You  edited successfully");

        } catch (ServiceException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
        }

        return REDIRECT_ON_VIEW_ACTIVITY_PAGE + "&id=" + activity.getId();
    }

    private String doGet(HttpServletRequest request, HttpServletResponse response) {
        PathUtils.saveCurrentPath(request, response);

        int activityId = Integer.parseInt(request.getParameter("id"));
        Activity activity = ActivityService.getInstance().getActivityById(activityId);
        request.setAttribute("activity", activity);

         request.setAttribute("categories", Category.values());


        return FORWARD_JSP_EDIT_ACTIVITY_PAGE;
    }
}
