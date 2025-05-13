package com.cricademy.controller;

import com.cricademy.services.LoginService;
import com.cricademy.util.CookieUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final LoginService loginService = new LoginService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String inputPassword = request.getParameter("password");

        boolean isValid = true;

        // Validate username
        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("usernameError", "Username is required.");
            isValid = false;
        }

        // Validate password
        if (inputPassword == null || inputPassword.trim().isEmpty()) {
            request.setAttribute("passwordError", "Password is required.");
            isValid = false;
        }

        // If validation fails, reload form with errors and old data
        if (!isValid) {
            request.setAttribute("username", username);
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }

        // Authenticate
        String role = loginService.authenticate(username, inputPassword);

        if (role != null) {
            // Login successful
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            CookieUtil.addCookie(response, "role", role, 60 * 60); // 1 hour validity

            if ("admin".equalsIgnoreCase(role)) {
                response.sendRedirect(request.getContextPath() + "/admin");
            } else {
                response.sendRedirect(request.getContextPath() + "/home");
            }
        } else {
            // Login failed due to wrong credentials
            request.setAttribute("error", "Invalid username or password.");
            request.setAttribute("username", username); // preserve entered username
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        }
    }
}
