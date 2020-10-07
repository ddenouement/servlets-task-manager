package controller;

import command.util.PathUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeController extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher disp = request.getRequestDispatcher("index.jsp");

        PathUtils.saveCurrentPath(request,response);
        disp.forward(request, response);
    }
}
