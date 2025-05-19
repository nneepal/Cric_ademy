package com.cricademy.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

import com.cricademy.util.CookieUtil;
import com.cricademy.util.SessionUtil;

/**
 * Servlet implementation class Logout
 * Handles user logout by invalidating session and deleting authentication cookies,
 * then redirects to the login page.
 * 
 * @author Arpan Nepal
 * LMUID: 23048647
 */
@WebServlet("/logout")
public class Logout extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles POST request to logout user by invalidating session and deleting 'role' cookie.
     * Afterwards, redirects the user to the login page.
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Delete the 'role' cookie from client
        CookieUtil.deleteCookie(response, "role");

        // Invalidate the current session if exists
        SessionUtil.invalidateSession(request);

        // Redirect to login page after logout
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
