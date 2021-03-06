package commands.impl.user;

import commands.util.HttpAction;
import commands.ICommand;
import commands.util.PathUtils;
import model.Motif;
import service.RequestService;
import service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Command to create a request for activity
 * @Author Yuliia Aleksandrova
 */
public class CreateRequestCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) throws IOException, ServletException {
        String previousPath = PathUtils.getSavedPath(request, response);
        int userId = Integer.parseInt(request.getParameter("user_id"));
        String motifParam = request.getParameter("motif");
        Motif motif = Motif.valueOf(motifParam);
        int id = 0;
        if (motif == Motif.ADD) {
            int actId = Integer.parseInt(request.getParameter("activity_id"));
            try {
                id = RequestService.getInstance().createAddRequest(actId, userId);
                request.getSession().setAttribute("infoMessage", "You created request with id " + id);

            } catch (ServiceException e) {
                request.getSession(true).setAttribute("errorMessage", e.getMessage());
            }
        }
        if (motif == Motif.REMOVE) {
            int taskId = Integer.parseInt(request.getParameter("task_id"));
            try {
                id = RequestService.getInstance().createRemoveRequest(taskId, userId);
                request.getSession().setAttribute("infoMessage", "You created request with id " + id);

            } catch (ServiceException e) {
                request.getSession(true).setAttribute("errorMessage", e.getMessage());
            }
        }
        return previousPath;
    }
}
