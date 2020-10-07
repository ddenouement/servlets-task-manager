package command.impl;

import com.mysql.cj.Session;
import command.util.HttpAction;
import command.ICommand;
import command.util.PathUtils;
import dao.ActivityRepository;
import model.Activity;
import model.Category;
import model.User;
import service.ActivityService;
import service.ServiceException;
import service.UserService;
import util.DBFields;
import util.SortActivitiesFields;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ListActivitiesCommand implements ICommand {

    private static final String ACTIVITIES_LIST_PAGE_JSP = "activitiesPage.jsp";
    private static final String REDIRECT_ACTIVITIES_LIST_PAGE = "controller?command=activities";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action)  {
        String result = null;

        if (HttpAction.GET == action) {
            result = doGet(request, response);

        } else if (HttpAction.POST == action) {
            result = doPost(request, response);

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

        SortActivitiesFields defaultSort = SortActivitiesFields.ACTIVITY_NAME;
        String sortBy = defaultSort.getValue();
        int  sortId = defaultSort.ordinal();
        String sortByParam  = request.getParameter("sortBy");
        if(sortByParam!= null){
            try{
                sortId = Integer.parseInt(sortByParam);
                sortBy = SortActivitiesFields.values()[sortId].getValue();
            }
            catch (NumberFormatException  | ArrayIndexOutOfBoundsException e){

            }
        }
        request.setAttribute("sortBy", sortId);

        List<Activity> activities = ActivityService.getInstance().getAllActivitiesSorted(sortBy);
        request.setAttribute("activities", activities);
        request.setAttribute("categories", Category.values());

        return ACTIVITIES_LIST_PAGE_JSP;
    }
}
