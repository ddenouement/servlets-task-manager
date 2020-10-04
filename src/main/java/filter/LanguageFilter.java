package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

public class LanguageFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            if (request.getParameter("language") == null) {
                Locale locale = request.getLocale();

                String qs = req.getQueryString();
                if (qs == null || qs.isEmpty()) {
                    res.sendRedirect(req.getRequestURI() + "?language=" + locale.getLanguage());
                } else {
                    res.sendRedirect(req.getRequestURI() + "?" + qs + "&language=" + locale.getLanguage());
                }
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
