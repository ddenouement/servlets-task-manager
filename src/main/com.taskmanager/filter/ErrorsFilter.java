package filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ErrorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException{

        HttpServletRequest servletRequestHttp = (HttpServletRequest) servletRequest;
        if (servletRequestHttp.getSession().getAttribute("errorMessage") != null) {
            servletRequest.setAttribute("errorMessage", servletRequestHttp.getSession().getAttribute("errorMessage"));
            servletRequestHttp.getSession().removeAttribute("errorMessage");
        }
        if (servletRequestHttp.getSession().getAttribute("infoMessage") != null) {
            servletRequest.setAttribute("infoMessage", servletRequestHttp.getSession().getAttribute("infoMessage"));
            servletRequestHttp.getSession().removeAttribute("infoMessage");
        }
        filterChain.doFilter(servletRequestHttp, servletResponse);
    }



    @Override
    public void destroy() {

    }
}
