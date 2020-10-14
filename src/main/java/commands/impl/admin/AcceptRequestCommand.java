package commands.impl.admin;

import commands.util.HttpAction;
import commands.ICommand;
import commands.util.ParameterException;
import commands.util.ParameterGetter;
import commands.util.PathUtils;
import service.RequestService;
import service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Command that accepts request by its id
 * @Author Yuliia
 */
public class AcceptRequestCommand implements ICommand {

    /**
     * Check for ADMIN role
     * Get 'id' parameter from ServletRequest and accept the request.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param action POST
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
                RequestService.getInstance().acceptRequestById(reqId);
                request.getSession().setAttribute("infoMessage", "Accepted");
            } catch (ServiceException | ParameterException e) {
                request.getSession().setAttribute("errorMessage", e.getMessage());
            }
        }
        else {
            request.getSession().setAttribute("errorMessage", "Access denied");
        }
        return previousPath;
    }


}
