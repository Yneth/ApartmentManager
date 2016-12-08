package ua.abond.lab4.web.filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class LocaleCookieFilter implements Filter {
    private static final String LANG_KEY = "lang";
    private static final int COOKIE_AGE = 30 * 24 * 3600;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String lang = request.getParameter("lang");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (lang != null) {
            Cookie cookie = new Cookie("lang", lang);
            cookie.setMaxAge(COOKIE_AGE);
            resp.addCookie(cookie);
            request.setAttribute("lang", lang);
        } else {
            Arrays.stream(req.getCookies()).
                    filter(c -> LANG_KEY.equals(c.getName())).
                    findFirst().
                    map(Cookie::getValue).
                    ifPresent(v -> req.setAttribute(LANG_KEY, v));
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
