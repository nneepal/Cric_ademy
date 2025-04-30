package com.cricademy.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.cricademy.util.CookieUtil;
import com.cricademy.util.SessionUtil;

@WebFilter(asyncSupported = true, urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

    private static final String LOGIN = "/login";
    private static final String REGISTER = "/register";
    private static final String HOME = "/home";
    private static final String ABOUT = "/about";
    private static final String CONTACT = "/contact";
    private static final String ADMIN = "/admin";
    private static final String ROOT = "/";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Optional init logic
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();

        // Allow static resources
        if (uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png") || uri.endsWith(".jpg")) {
            chain.doFilter(request, response);
            return;
        }

        boolean isLoggedIn = SessionUtil.getAttribute(req, "username") != null;
        String role = CookieUtil.getCookie(req, "role") != null ? CookieUtil.getCookie(req, "role").getValue() : null;

        boolean isPublicPath = uri.endsWith(LOGIN) || uri.endsWith(REGISTER) || 
                               uri.endsWith(HOME) || uri.endsWith(ABOUT) || uri.endsWith(ROOT);

        if (isPublicPath) {
            chain.doFilter(request, response);
            return;
        }

        if ("admin".equals(role)) {
            if (uri.startsWith(req.getContextPath() + ADMIN)) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + ADMIN);
            }
        } else if ("player".equals(role)) {
            if (uri.endsWith(HOME) || uri.endsWith(ABOUT) || uri.endsWith(CONTACT)) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + HOME);
            }
        } else {
            res.sendRedirect(req.getContextPath() + LOGIN);
        }
    }

    @Override
    public void destroy() {
        // Optional cleanup
    }
}
