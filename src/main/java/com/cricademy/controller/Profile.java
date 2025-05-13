package com.cricademy.controller;

import com.cricademy.model.PlayerModel;
import com.cricademy.services.ProfileService;
import com.cricademy.util.CookieUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;  // Added this import
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/profile")
public class Profile extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProfileService profileService = new ProfileService();

    public Profile() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the current session (if it exists)
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String username = (String) session.getAttribute("username");
        
        // Get user data
        PlayerModel user = profileService.getUserDetails(username);

        if (user != null) {
            request.setAttribute("user", user); // set user in request scope
        }

        // Get role from cookie
        String role = null;
        Cookie roleCookie = CookieUtil.getCookie(request, "role");
        if (roleCookie != null) {
            role = roleCookie.getValue();
        }
        request.setAttribute("role", role);

        request.getRequestDispatcher("WEB-INF/pages/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // You can use doPost for update if needed, currently just forwarding to doGet
        doGet(request, response);
    }
}