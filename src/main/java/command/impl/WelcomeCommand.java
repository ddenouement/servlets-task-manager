package command.impl;

import command.ICommand;
import command.util.HttpAction;
import command.util.PathUtils;
import model.Activity;
import model.Category;
import service.ActivityService;
import util.SortActivitiesFields;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class WelcomeCommand implements ICommand {

    private static final String MAIN_PAGE_JSP = "index.jsp";

    private String doGet(HttpServletRequest request, HttpServletResponse response) {
        PathUtils.saveCurrentPath(request, response);
        response.setCharacterEncoding("UTF-8");
         return MAIN_PAGE_JSP;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) {
        if(action == HttpAction.GET)
            return doGet(request,response);
        return null;
    }
}
