package com.cricademy.filter;

import com.cricademy.util.CookieUtil;
import com.cricademy.util.SessionUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AuthenticationFilter is a servlet filter that handles user authentication and authorization.
 * It restricts access to protected resources based on user session or cookie data.
 * 
 * Features:
 * - Allows unrestricted access to public URLs and static resources (CSS, JS, images, fonts).
 * - Checks user session and cookies for authentication info (username and role).
 * - Grants access to admin-only pages only for users with "admin" role.
 * - Grants access to profile page for users with "admin" or "player" role.
 * - Redirects unauthorized or unauthenticated users to the login page.
 * 
 * Author: Arpan Nepal
 * LMUID: 23048647
 */
@WebFilter(asyncSupported = true, urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

    /**
     * Array of publicly accessible URL paths that don't require authentication.
     */
    private static final String[] PUBLIC_PATHS = {
        "/", "/home", "/about", "/login", "/register", "/contact", "/players"
    };

    /**
     * The main filtering method that intercepts requests and applies authentication logic.
     * 
     * @param request The incoming ServletRequest (cast to HttpServletRequest)
     * @param response The outgoing ServletResponse (cast to HttpServletResponse)
     * @param chain The filter chain to pass control to the next filter or resource
     * @throws IOException If an input or output error occurs during filtering
     * @throws ServletException If the request cannot be handled
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Cast generic servlet request/response to HTTP servlet request/response
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Get the requested URI to decide filtering rules
        String uri = req.getRequestURI();

        // Retrieve username and role from session attributes
        String username = (String) SessionUtil.getAttribute(req, "username");
        String role = (String) SessionUtil.getAttribute(req, "role");

        // If role is not found in session, fallback to check cookies
        if (role == null && CookieUtil.getCookie(req, "role") != null) {
            role = CookieUtil.getCookie(req, "role").getValue();
        }

        // Allow access to static resources such as CSS, JS, images, fonts without authentication
        if (uri.matches(".*(\\.css|\\.js|\\.png|\\.jpg|\\.jpeg|\\.gif|\\.svg|\\.woff|\\.woff2|\\.ttf|\\.eot)$") ||
            uri.matches(".*(\\.CSS|\\.JS|\\.PNG|\\.JPG|\\.JPEG|\\.GIF|\\.SVG|\\.WOFF|\\.WOFF2|\\.TTF|\\.EOT)$")) {
            chain.doFilter(request, response);
            return;
        }

        // Allow access to public paths for everyone without authentication
        for (String path : PUBLIC_PATHS) {
            // Compare request URI with context path + public path or just path (for root)
            if (uri.equals(req.getContextPath() + path) || uri.equals(path)) {
                chain.doFilter(request, response);
                return;
            }
        }

        // Admin-only path protection: only users with role "admin" can access /admin
        if (uri.equals(req.getContextPath() + "/admin")) {
            System.out.println("Filter: username: " + username);
            System.out.println("Filter: role: " + role);
            if (username != null && "admin".equals(role)) {
                chain.doFilter(request, response);
            } else {
                // Redirect unauthorized users to login page
                res.sendRedirect(req.getContextPath() + "/login");
            }
            return;
        }

        // Profile page accessible for both admin and player roles
        if (uri.equals(req.getContextPath() + "/profile")) {
            if (username != null && ("admin".equals(role) || "player".equals(role))) {
                chain.doFilter(request, response);
            } else {
                // Redirect unauthorized users to login page
                res.sendRedirect(req.getContextPath() + "/login");
            }
            return;
        }

        // By default, block all other requests and redirect to login
        res.sendRedirect(req.getContextPath() + "/login");
    }

    /**
     * Filter initialization method (not used here).
     */
    @Override
    public void init(FilterConfig filterConfig) {}

    /**
     * Filter destruction method (not used here).
     */
    @Override
    public void destroy() {}
}
