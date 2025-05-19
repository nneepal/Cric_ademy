package com.cricademy.controller;

import com.cricademy.model.Player;
import com.cricademy.services.PlayerService;
import com.cricademy.config.DbConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 * Servlet implementation class Players
 * Handles displaying player list with optional search functionality.
 * Retrieves player data from the database and forwards it to players.jsp.
 * 
 * Author: Arpan Nepal
 * LMUID: 23048647
 */
@WebServlet("/players")
public class Players extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Players() {
        super();
    }

    /**
     * Handles GET requests to fetch and display player list.
     * If a 'search' query parameter is provided, filters players by name.
     * Forwards results to players.jsp.
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchQuery = request.getParameter("search");

        try (Connection conn = DbConfig.getDbConnection()) {
            PlayerService service = new PlayerService(conn);
            List<Player> players;

            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                players = service.searchPlayersByName(searchQuery.trim());
            } else {
                players = service.getAllPlayers();
            }

            request.setAttribute("players", players);
            request.setAttribute("search", searchQuery);
            request.getRequestDispatcher("WEB-INF/pages/players.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to load players.");
            request.getRequestDispatcher("WEB-INF/pages/players.jsp").forward(request, response);
        }
    }

    /**
     * Forwards POST requests to the doGet method.
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
