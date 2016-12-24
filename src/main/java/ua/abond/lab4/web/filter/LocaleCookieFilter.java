package ua.abond.lab4.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class LocaleCookieFilter extends HttpFilter {
    private static final int COOKIE_AGE = 30 * 24 * 3600;

    public static final String LANG_KEY = "lang";
    public static final String LOCALE_KEY = "locale";

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String lang = request.getParameter(LANG_KEY);
        if (lang != null) {
            Cookie cookie = new Cookie(LANG_KEY, lang);
            cookie.setMaxAge(COOKIE_AGE);

            response.addCookie(cookie);
            request.setAttribute(LOCALE_KEY, Locale.forLanguageTag(lang));
        } else {
            Arrays.stream(request.getCookies()).
                    filter(c -> LANG_KEY.equals(c.getName())).
                    findFirst().
                    map(Cookie::getValue).
                    ifPresent(v -> request.setAttribute(LOCALE_KEY, Locale.forLanguageTag(v)));
        }
        chain.doFilter(request, response);
    }
}
