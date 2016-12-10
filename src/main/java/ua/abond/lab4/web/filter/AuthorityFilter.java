package ua.abond.lab4.web.filter;

import org.apache.log4j.Logger;
import ua.abond.lab4.web.dto.UserSessionDTO;
import ua.abond.lab4.web.mapper.UserSessionDTORequestMapper;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class AuthorityFilter extends HttpFilter {
    private static final Logger logger = Logger.getLogger(AuthorityFilter.class);
    public static final String AUTH_PARAM = "authority";

    private String authority;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        authority = filterConfig.getInitParameter(AUTH_PARAM);
        Objects.requireNonNull(authority, "Authority parameter was not specified.");
    }

    @Override
    protected void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final String auth = authority;

        UserSessionDTO user = new UserSessionDTORequestMapper().map(request);
        if (isAuthorized(user, auth)) {
            chain.doFilter(request, response);
        } else {
            logger.debug("Unauthorized access.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private boolean isAuthorized(UserSessionDTO user, String auth) {
        return user != null
                && user.getAuthority() != null
                && user.getAuthority().getName() != null
                && auth.equalsIgnoreCase(user.getAuthority().getName());
    }
}
