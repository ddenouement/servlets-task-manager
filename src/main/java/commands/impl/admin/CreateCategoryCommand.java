package commands.impl.admin;

import commands.ICommand;
import commands.util.HttpAction;
import model.Category;
import model.Role;
import service.CategoryService;
import service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Command to create new category
 * @Author Yuliia Aleksandrova
 */
public class CreateCategoryCommand implements ICommand {

    private static final String JSP_ADD_CATEGORY_PAGE = "admin/category/add.jsp";
    private static final String REDIRECT_ACTIVITIES_LIST_PAGE = "controller?command=activities";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) {
        String result = null;
        if (HttpAction.POST == action) {
            result = doPost(request, response);

        } else if (action == HttpAction.GET) {
            result = doGet(request, response);
        }
        return result;
    }

    /**
     * Check for ADMIN role
     * Get parameters from request and create Category
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return String
     */
    private String doPost(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        //check Admin privilege
        if (session.getAttribute("userRole") != null
                && session.getAttribute("userRole").equals("ADMIN")) {
            Category category = new Category();
            String nameEn = request.getParameter("nameEn");
            String nameUa = request.getParameter("nameUa");
            String name = request.getParameter("name");
            category.setName(name);
            category.setNameEn(nameEn);
            category.setNameUa(nameUa);
            try {
                CategoryService.getInstance().createCategory(category);
                request.getSession().setAttribute("infoMessage", "You created category successfully");
            } catch (ServiceException e) {
                request.getSession(true).setAttribute("errorMessage", e.getMessage());
                return REDIRECT_ACTIVITIES_LIST_PAGE;
            }
        }
        return REDIRECT_ACTIVITIES_LIST_PAGE;
    }

    private String doGet(HttpServletRequest request, HttpServletResponse response) {

        String role =  (String) request.getSession().getAttribute("userRole");
        if(role==null||Role.valueOf(role.toUpperCase())!= Role.ADMIN){
            return null;
        }
        return JSP_ADD_CATEGORY_PAGE;
    }
}
