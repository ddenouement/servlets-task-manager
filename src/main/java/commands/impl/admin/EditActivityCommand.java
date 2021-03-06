package commands.impl.admin;

import commands.util.HttpAction;
import commands.ICommand;
import commands.util.PathUtils;
import model.Activity;
import model.Category;
import model.Role;
import service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
/**
 * Command to edit existing activity
 * @Author Yuliia Aleksandrova
 */
public class EditActivityCommand implements ICommand {
    private static String REDIRECT_ON_VIEW_ACTIVITY_PAGE = "controller?command=viewActivity";

    private static String FORWARD_JSP_EDIT_ACTIVITY_PAGE = "/admin/activity/edit.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) {
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
        String categoryParam = request.getParameter("categoryId");
        String descrParamEn = request.getParameter("descriptionEn");
        String descrParamUa = request.getParameter("descriptionUa");
        String descrParam = request.getParameter("description");
        String activityIdParam = request.getParameter("id");
        int activityId = Integer.parseInt(activityIdParam);
        int categoryId = Integer.parseInt(categoryParam);

        Activity activity = new Activity();
        activity.setId(activityId);
        activity.setDescription(descrParam);
        activity.setDescriptionUa(descrParamUa);
        activity.setDescriptionEn(descrParamEn);
        activity.setName(nameParam);
        activity.setCategory(new Category(categoryId));
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

        String role =  (String) request.getSession().getAttribute("userRole");
        if(role==null||Role.valueOf(role.toUpperCase())!= Role.ADMIN){
            return null;
        }

        PathUtils.saveCurrentPath(request, response);

        int activityId = Integer.parseInt(request.getParameter("id"));
        Optional<Activity> activity = ActivityService.getInstance().getActivityById(activityId);

        if(!activity.isPresent())
            request.setAttribute("errorMessage", "No such activity found");
        request.setAttribute("activity", activity.get());

        List<Category> categories  =  CategoryService.getInstance().getAllCategories();
        request.setAttribute("categories", categories);

        return FORWARD_JSP_EDIT_ACTIVITY_PAGE;
    }
}
