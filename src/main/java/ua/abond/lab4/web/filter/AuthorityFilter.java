package ua.abond.lab4.web.filter;

import org.apache.log4j.Logger;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.web.mapper.UserSessionRequestMapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class AuthorityFilter implements Filter {
    private static final Logger logger = Logger.getLogger(AuthorityFilter.class);
    public static final String AUTH_PARAM = "authority";

    private String authority;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        authority = filterConfig.getInitParameter(AUTH_PARAM);
        Objects.requireNonNull(authority, "Authority parameter was not specified.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final String auth = authority;

        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            throw new ServletException("AuthorityFilter supports only HTTP requests.");
        }
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        User user = new UserSessionRequestMapper().map(httpReq);
        if (isAuthorized(user, auth)) {
            chain.doFilter(request, response);
        } else {
            logger.debug("Unauthorized access.");
            httpResp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    public void destroy() {
    }

    private boolean isAuthorized(User user, String auth) {
        return user != null
                && user.getAuthority() != null
                && user.getAuthority().getName() != null
                && auth.equalsIgnoreCase(user.getAuthority().getName());
    }
}
