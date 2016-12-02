package ua.abond.lab4.web.filter;

import org.apache.log4j.Logger;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.web.mapper.UserSessionRequestMapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorityFilter implements Filter {
    private static final Logger logger = Logger.getLogger(AuthorityFilter.class);
    private static final String AUTH_PARAM = "authority";

    private String authority;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        authority = filterConfig.getInitParameter(AUTH_PARAM);
        if (authority == null) {
            throw new RuntimeException("Authority parameter was not given.");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final String auth = authority;

        final String errorMsg = "Expected %s but was %s";
        if (!(request instanceof HttpServletRequest)) {
            logger.error(String.format(errorMsg, "HttpServletRequest", request.getClass().getSimpleName()));
            return;
        }
        if (!(response instanceof HttpServletResponse)) {
            logger.error(String.format(errorMsg, "HttpServletResponse", response.getClass().getSimpleName()));
            return;
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
