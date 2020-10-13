package commands.impl;

import commands.util.HttpAction;
import commands.ICommand;
import commands.util.ParameterGetter;
import commands.util.PathUtils;

import model.Activity;
import model.Category;
import service.ActivityService;
import service.ServiceException;
import sun.security.validator.ValidatorException;
import util.SortActivitiesFields;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ListActivitiesCommand implements ICommand {

    private static int page = 1;
    private static int recordsPerPage = 3;
    private static final String ACTIVITIES_LIST_PAGE_JSP = "activitiesPage.jsp";
    private static final String REDIRECT_ACTIVITIES_LIST_PAGE = "controller?command=activities";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) {
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

            try {
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
        int sortId = defaultSort.ordinal();

        try {
            sortId = ParameterGetter.getIntParam(request, "sortBy");
            sortBy = SortActivitiesFields.values()[sortId].getValue();
        } catch (ValidatorException | ArrayIndexOutOfBoundsException ignored) {
        }
        request.setAttribute("sortBy", sortId);
        try {
            page = ParameterGetter.getIntParam(request, "page");
        } catch (ValidatorException ignored) {
        }

        int offset = (page - 1) * recordsPerPage;
        List<Activity> activities = ActivityService.getInstance().getAllActivitiesSortedPaged(sortBy,recordsPerPage, offset);
        int numOfRequests = ActivityService.getInstance().getCountOfActivities();
        int noOfPages = (int) Math.ceil(numOfRequests * 1.0 / recordsPerPage);

        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("activities", activities);
        request.setAttribute("categories", Category.values());

        return ACTIVITIES_LIST_PAGE_JSP;
    }
}
