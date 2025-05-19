package com.cricademy.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class Home
 * 
 * Handles requests for the home page and processes form submissions.
 * 
 * @author Arpan Nepal
 * LMUID: 23048647
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/home", "/"})
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
    }

	/**
	 * Handles HTTP GET requests by forwarding to the home.jsp page.
	 *
	 * @param request  HttpServletRequest object containing client request
	 * @param response HttpServletResponse object for sending response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs during request forwarding
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/pages/home.jsp").forward(request, response);
    }

	/**
	 * Handles HTTP POST requests for form submissions on the home page.
	 * Retrieves form parameters 'username' and 'message' and sets them as
	 * request attributes before forwarding to the home.jsp page.
	 *
	 * @param request  HttpServletRequest object containing client request
	 * @param response HttpServletResponse object for sending response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs during request forwarding
	 */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String message = request.getParameter("message");

        request.setAttribute("username", username);
        request.setAttribute("message", message);

        request.getRequestDispatcher("WEB-INF/pages/home.jsp").forward(request, response);
    }

}
