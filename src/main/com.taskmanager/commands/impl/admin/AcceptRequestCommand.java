package commands.impl.admin;

import commands.util.HttpAction;
import commands.ICommand;
import commands.util.ParameterGetter;
import commands.util.PathUtils;
import service.RequestService;
import service.ServiceException;
import sun.security.validator.ValidatorException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AcceptRequestCommand implements ICommand {

    //only POST
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) throws IOException, ServletException {

        String previousPath = PathUtils.getSavedPath(request, response);
        try {
            int reqId = ParameterGetter.getIntParam(request,"id");
            RequestService.getInstance().acceptRequestById(reqId);
            request.getSession().setAttribute("infoMessage", "Accepted");
        } catch (ServiceException | ValidatorException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
        }
        return previousPath;
    }


}