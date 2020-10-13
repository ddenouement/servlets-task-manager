package commands.impl;

import commands.util.HttpAction;
import commands.ICommand;
import commands.util.PathUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LanguageCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) {

        String result = null;
        if (HttpAction.POST == action) {
            result = doPost(request, response);
        }
        return result;
    }

    private String doPost(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(true);

        String language = request.getParameter("lang");

        session.setAttribute("lang", language);

        return PathUtils.getSavedPath(request, response);
    }

}
