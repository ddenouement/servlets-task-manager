package command.impl.admin;

import command.HttpAction;
import command.ICommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StatsCommand implements ICommand {
    private static String STATS_VIEW_JSP = "/admin/stats/view.jsp";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) throws IOException, ServletException {
       if( action == HttpAction.GET){
           return doGet(request, response);
       }
       return null;
    }

    private String doGet(HttpServletRequest request, HttpServletResponse response) {

        return STATS_VIEW_JSP;
    }
}
