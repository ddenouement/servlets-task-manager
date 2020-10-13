package command.impl.admin;

import command.ICommand;
import command.util.HttpAction;
import command.util.PathUtils;
import model.Activity;
import model.Category;
import service.ActivityService;
import service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class CreateActivityCommand implements ICommand {

    private static final String JSP_ADD_ACTIVITY_PAGE ="admin/activity/add.jsp";

    private static final String REDIRECT_ACTIVITIES_LIST_PAGE = "controller?command=activities";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action)  {
        String result = null;
        if (HttpAction.POST == action) {
            result = doPost(request, response);

        }else if (action == HttpAction.GET) {
            result = doGet(request, response);
        }
        return result;
    }

    //add new activity (ADMIN only)
    private String doPost(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        //check Admin privilege
        if (session.getAttribute("userRole") != null
                && session.getAttribute("userRole").equals("ADMIN")) {
            Activity activity = new Activity();
            String descrEn = request.getParameter("descriptionEn");
            String descrUa = request.getParameter("descriptionUa");
            String descr = request.getParameter("description");
            String nameEn = request.getParameter("nameEn");
            String nameUa = request.getParameter("nameUa");
            String name = request.getParameter("name");
            String category = request.getParameter("category");
            activity.setCategory(category);
            activity.setName(name);
            activity.setNameEn(nameEn);
            activity.setNameUa(nameUa);
            activity.setDescriptionEn(descrEn);
            activity.setDescriptionUa(descrUa);
            activity.setDescription(descr);

            try{
                ActivityService.getInstance().createActivity(activity);
                request.getSession().setAttribute("infoMessage", "You created activity successfully");
            } catch (ServiceException e) {
                request.getSession(true).setAttribute("errorMessage", e.getMessage());
                return REDIRECT_ACTIVITIES_LIST_PAGE;
            }
        }
        return REDIRECT_ACTIVITIES_LIST_PAGE;
    }
    private String doGet(HttpServletRequest request, HttpServletResponse response) {
        PathUtils.saveCurrentPath(request, response);

        request.setAttribute("categories", Category.values());

        return  JSP_ADD_ACTIVITY_PAGE;
    }

}
