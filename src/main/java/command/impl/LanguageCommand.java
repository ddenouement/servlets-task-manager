package command.impl;

import command.HttpAction;
import command.ICommand;
import command.PathUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LanguageCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) throws IOException, ServletException {

        String result = null;
        if (HttpAction.POST == action) {
            result = doPost(request, response);
        }
        return result;
    }

    private String doPost(HttpServletRequest request, HttpServletResponse response) {



        HttpSession session = request.getSession(true);

        String language = request.getParameter("language");

        session.setAttribute("language", language);

        System.out.println("Language changed to"+language);
        return null;
    }

}
