package com.cricademy.controller;

import com.cricademy.services.LoginService;
import com.cricademy.util.CookieUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Servlet handling user login functionality.
 * Processes GET requests to show the login page,
 * and POST requests to authenticate users and start sessions.
 * 
 * @author Arpan Nepal
 * LMUID: 23048647
 */
@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final LoginService loginService = new LoginService();

    /**
     * Forwards GET requests to the login.jsp page.
     *
     * @param req  HttpServletRequest object
     * @param resp HttpServletResponse object
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    /**
     * Handles POST requests to authenticate users.
     * Validates input, authenticates with LoginService, sets session and cookies,
     * and redirects users based on their role.
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String inputPassword = request.getParameter("password");

        boolean isValid = true;

        // Validate username presence
        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("usernameError", "Username is required.");
            isValid = false;
        }

        // Validate password presence
        if (inputPassword == null || inputPassword.trim().isEmpty()) {
            request.setAttribute("passwordError", "Password is required.");
            isValid = false;
        }

        // If validation fails, reload login page with error messages
        if (!isValid) {
            request.setAttribute("username", username);
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }

        // Authenticate credentials and retrieve role if valid
        String role = loginService.authenticate(username, inputPassword);

        if (role != null) {
            // Successful login: set session attribute and cookie for role
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            CookieUtil.addCookie(response, "role", role, 60 * 30); // 30 minutes expiry

            // Redirect based on role
            if ("admin".equalsIgnoreCase(role)) {
                response.sendRedirect(request.getContextPath() + "/admin");
            } else {
                response.sendRedirect(request.getContextPath() + "/home");
            }
        } else {
            // Authentication failed: show error and preserve username input
            request.setAttribute("error", "Invalid username or password.");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        }
    }
}
