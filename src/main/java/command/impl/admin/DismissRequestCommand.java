package command.impl.admin;

import command.HttpAction;
import command.ICommand;
import command.PathUtils;
import service.RequestService;
import service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DismissRequestCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) throws IOException, ServletException {

        String previousPath = PathUtils.getSavedPath(request,response);
        int reqId = Integer.parseInt(request.getParameter("id"));
        try{
            RequestService.getInstance().dismissRequestById(reqId);
            request.getSession().setAttribute("infoMessage", "You rejected request successfully");

        }catch (ServiceException a){
             request.setAttribute("errorMessage", a.getMessage());
        }

        return previousPath;
    }
}
