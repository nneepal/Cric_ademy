package com.cricademy.controller;

import com.cricademy.services.LoginService;
import com.cricademy.util.CookieUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final LoginService loginService = new LoginService();

    public Login() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String role = loginService.authenticateUser(username, password);

        if (role != null) {
            // Successful login: Create cookie based on role
            CookieUtil.addCookie(response, "role", role, 60 * 60); // Cookie valid for 1 hour

            // Also you may set username in session if needed (optional)
            request.getSession().setAttribute("username", username);

            if ("admin".equals(role)) {
                response.sendRedirect(request.getContextPath() + "/admin");
            } else {
                response.sendRedirect(request.getContextPath() + "/home");
            }
        } else {
            // Invalid login: Show error
            request.setAttribute("loginError", "Invalid username or password.");
            request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);
        }
    }
}
