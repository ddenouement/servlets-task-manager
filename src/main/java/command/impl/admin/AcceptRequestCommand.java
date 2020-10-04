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

public class AcceptRequestCommand   implements ICommand {

        //only POST
        @Override
        public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) throws IOException, ServletException {

                 String previousPath = PathUtils.getSavedPath(request, response);
                int reqId = Integer.parseInt(request.getParameter("id"));
                try{
                    RequestService.getInstance().acceptRequestById(reqId);
                    request.getSession().setAttribute("infoMessage", "Accepted");

                } catch (ServiceException e) {
                    request.getSession().setAttribute("errorMessage", e.getMessage());
                }
            return previousPath;
            }


}
