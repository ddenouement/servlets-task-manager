package commands.impl;

import commands.ICommand;
import commands.util.HttpAction;
import commands.util.PathUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
