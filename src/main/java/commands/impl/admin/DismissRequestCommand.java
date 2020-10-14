package commands.impl.admin;

import commands.util.HttpAction;
import commands.ICommand;
import commands.util.ParameterException;
import commands.util.PathUtils;
import commands.util.ParameterGetter;
import service.RequestService;
import service.ServiceException;
import sun.security.validator.ValidatorException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Command to  reject user request
 * @Author Yuliia Aleksandrvoa
 */
public class DismissRequestCommand implements ICommand {

    /**
     * Check for ADMIN role
     * Get 'id' parameter from ServletRequest and reject the request.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param action   POST
     * @return String
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) throws IOException, ServletException {

        String previousPath = PathUtils.getSavedPath(request, response);

        HttpSession session = request.getSession(false);
        if (session.getAttribute("userRole") != null
                && session.getAttribute("userRole").equals("ADMIN")) {

            try {
                int reqId = ParameterGetter.getIntParam(request, "id");
                RequestService.getInstance().dismissRequestById(reqId);
                request.getSession().setAttribute("infoMessage", "You rejected request successfully");

            } catch (ServiceException | ParameterException a) {
                request.getSession().setAttribute("errorMessage", a.getMessage());
            }
        } else {
            request.getSession().setAttribute("errorMessage", "Access denied");
        }

        return previousPath;
    }
}
