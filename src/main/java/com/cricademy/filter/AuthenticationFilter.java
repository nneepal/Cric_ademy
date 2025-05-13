package com.cricademy.filter;

import com.cricademy.util.CookieUtil;
import com.cricademy.util.SessionUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(asyncSupported = true, urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

    private static final String[] PUBLIC_PATHS = {
        "/", "/home", "/about", "/login", "/register", "/contact"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        
        // Check session for username and role
        String username = (String) SessionUtil.getAttribute(req, "username");
        String role = (String) SessionUtil.getAttribute(req, "role");
        
        // Fallback to cookie if role not found in session
        if (role == null && CookieUtil.getCookie(req, "role") != null) {
            role = CookieUtil.getCookie(req, "role").getValue();
        }

        // Allow static resources (CSS, JS, images, fonts, etc.)
        if (uri.matches(".*(\\.css|\\.js|\\.png|\\.jpg|\\.jpeg|\\.gif|\\.svg|\\.woff|\\.woff2|\\.ttf|\\.eot)$")) {
            chain.doFilter(request, response);
            return;
        }

        // Allow public paths to everyone
        for (String path : PUBLIC_PATHS) {
            if (uri.equals(req.getContextPath() + path) || uri.equals(path)) {
                chain.doFilter(request, response);
                return;
            }
        }

        // Admin-only path
        if (uri.equals(req.getContextPath() + "/admin")) {
            System.out.println("Filter: username: " + username);
            System.out.println("Filter: role: " + role);
            if (username != null && "admin".equals(role)) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + "/login");
            }
            return;
        }

        // Profile page – allow both admin and player
        if (uri.equals(req.getContextPath() + "/profile")) {
            if (username != null && ("admin".equals(role) || "player".equals(role))) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + "/login");
            }
            return;
        }

        // Default case – block and redirect to login
        res.sendRedirect(req.getContextPath() + "/login");
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}