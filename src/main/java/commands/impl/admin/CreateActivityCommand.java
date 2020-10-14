package commands.impl.admin;

import commands.ICommand;
import commands.util.HttpAction;
import commands.util.ParameterException;
import commands.util.ParameterGetter;
import commands.util.PathUtils;
import dao.DataSourceFactory;
import dao.RepositoryFactory;
import model.Activity;
import model.Category;
import service.ActivityService;
import service.CategoryService;
import service.ServiceException;
import sun.security.validator.ValidatorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Command to create new activity
 * @Author Yuliia Aleksandrova
 */
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

    /**
     * Check for ADMIN role
     * Get parameters from request and create Activity
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return String
     */
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
            int id_category  = 1;
            try{
                id_category = ParameterGetter.getIntParam(request, "categoryId");
            } catch (ParameterException e) {
                request.getSession(true).setAttribute("errorMessage", "Error with parameter category");
                return REDIRECT_ACTIVITIES_LIST_PAGE;
            }

            activity.setCategory(new Category(id_category));
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

        List<Category> categories  =  CategoryService.getInstance().getAllCategories();
        request.setAttribute("categories", categories);

        return  JSP_ADD_ACTIVITY_PAGE;
    }

}
