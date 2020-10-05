package command.impl.admin;

import command.util.HttpAction;
import command.ICommand;
import command.util.PathUtils;
import command.util.ParameterGetter;
import service.RequestService;
import service.ServiceException;
import sun.security.validator.ValidatorException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DismissRequestCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) throws IOException, ServletException {

        String previousPath = PathUtils.getSavedPath(request, response);
        try {
            int reqId = ParameterGetter.getIntParam(request, "id");
            RequestService.getInstance().dismissRequestById(reqId);
            request.getSession().setAttribute("infoMessage", "You rejected request successfully");

        } catch (ServiceException | ValidatorException a) {
            request.setAttribute("errorMessage", a.getMessage());
        }

        return previousPath;
    }
}
