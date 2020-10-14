package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
/**
 * Filter to encode request and response contents
 * @Author Yuliia Aleksandrova
 */
public class EncodingFilter implements Filter {

    private String encoding;
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        request.setCharacterEncoding(encoding);
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    public void init(FilterConfig fConfig)  {
        encoding = fConfig.getInitParameter("encoding");
    }

}
