
package util;

import com.mysql.cj.Session;
import model.User;

import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class MyTagHandler extends TagSupport {

    public int doStartTag() {
        JspWriter out = pageContext.getOut();
        try {
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            HttpSession session = request.getSession();
            User u = (User) session.getAttribute("user");
            if (u != null)
                out.print(u.getFirstName() + " " + u.getLastName());
        } catch (Exception ignored) {
        }
        return SKIP_BODY;
    }
}
