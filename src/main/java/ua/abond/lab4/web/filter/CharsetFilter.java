package ua.abond.lab4.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CharsetFilter extends HttpFilter {
    private static final String ENCODING_ATTR = "requestEncoding";
    private static final String DEFAULT_ENCODING = "UTF-8";

    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        encoding = filterConfig.getInitParameter(ENCODING_ATTR);
        if (encoding == null) {
            encoding = DEFAULT_ENCODING;
        }
    }

    @Override
    protected void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);

        chain.doFilter(request, response);
    }
}
