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
import java.util.List;
/**
 * Command to view all activities
 * @Author Yuliia Aleksandrova
 */
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

        } 
        return result;
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
