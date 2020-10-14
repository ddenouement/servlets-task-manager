
package util;

import dto.UserDTO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Custom tag class
 * @Author Yuliia Aleksandrova
 */

public class MyTagHandler extends TagSupport {

    public int doStartTag() {
        JspWriter out = pageContext.getOut();
        try {
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            HttpSession session = request.getSession();
            UserDTO u = (UserDTO) session.getAttribute("user");
            if (u != null)
                out.print(u.getFirstName() + " " + u.getLastName());
        } catch (Exception ignored) {
        }
        return SKIP_BODY;
    }
}
