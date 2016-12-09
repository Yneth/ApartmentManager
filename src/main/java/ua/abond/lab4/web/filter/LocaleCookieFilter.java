package ua.abond.lab4.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class LocaleCookieFilter extends HttpFilter {
    public static final String LANG_KEY = "lang";
    private static final int COOKIE_AGE = 30 * 24 * 3600;

    @Override
    protected void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String lang = request.getParameter(LANG_KEY);
        if (lang != null) {
            Cookie cookie = new Cookie(LANG_KEY, lang);
            cookie.setMaxAge(COOKIE_AGE);
            cookie.setSecure(true);

            response.addCookie(cookie);
            request.setAttribute(LANG_KEY, lang);
        } else {
            Arrays.stream(request.getCookies()).
                    filter(c -> LANG_KEY.equals(c.getName())).
                    findFirst().
                    map(Cookie::getValue).
                    ifPresent(v -> request.setAttribute(LANG_KEY, v));
        }
        chain.doFilter(request, response);
    }
}
