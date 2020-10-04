package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PathUtils {



   public static void saveCurrentPath(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);

        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();

        if (queryString == null) {
            session.setAttribute("previousPath", requestURI);
        } else {
            session.setAttribute("previousPath", requestURI + "?" + queryString);
        }
    }
   public static String getSavedPath(HttpServletRequest request, HttpServletResponse response){
        String previousPath = (String) request.getSession(false).getAttribute("previousPath");
        if (previousPath == null) {
            previousPath = "";
        }
        return previousPath;
    }
}
