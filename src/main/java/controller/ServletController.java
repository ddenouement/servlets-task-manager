package controller;

import commands.CommandFactory;
import commands.util.HttpAction;
import commands.ICommand;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;
/**
 * Controller that uses Command and Post-Redirect-Get patterns
 * Invokes command and redirect/forwards based on Http method
 * @Author Yuliia Aleksandrova
 */
public class ServletController extends HttpServlet {
    private static final Logger log = Logger.getLogger(String.valueOf(ServletController.class));

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        process(request, response, HttpAction.GET);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        process(request, response,  HttpAction.POST);
    }

    private void process(HttpServletRequest request,
                         HttpServletResponse response, HttpAction action) throws IOException, ServletException {

        String commandName = request.getParameter("command");
        ICommand command = CommandFactory.getCommandByName(commandName);
        String path = command.execute(request, response,action);

        //implementation of Post- Redirect - Get
        if (path != null) {
            if (action == HttpAction.GET) {
                RequestDispatcher disp = request.getRequestDispatcher(path);
                disp.forward(request, response);
            } else if (action == HttpAction.POST) {
                response.sendRedirect(path);
            }
        }
        if(path == null){
            RequestDispatcher disp = request.getRequestDispatcher("loginPage.jsp");
            disp.forward(request, response);
        }

    }

}
