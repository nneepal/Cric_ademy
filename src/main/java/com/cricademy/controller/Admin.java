package com.cricademy.controller;

import com.cricademy.config.DbConfig;
import com.cricademy.model.Player;
import com.cricademy.services.PlayerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet implementation class Admin.
 * Handles admin operations such as viewing, adding, updating, and deleting players.
 */
@WebServlet("/admin")
public class Admin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PlayerService playerService;

    /**
     * Initializes the servlet and sets up the PlayerService with a database connection.
     *
     * @throws ServletException if database connection fails
     */
    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Connection connection = DbConfig.getDbConnection();
            playerService = new PlayerService(connection);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    /**
     * Handles GET requests to display the admin dashboard and optionally populate the player form for editing.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException if server-side error occurs
     * @throws IOException if client-side error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            // If action is edit, fetch player details by email
            if ("edit".equals(action)) {
                String email = request.getParameter("email");
                Player player = playerService.getPlayerByEmail(email);
                request.setAttribute("playerToEdit", player);
            }

            // Get all players to display on admin dashboard
            List<Player> players = playerService.getAllPlayers();
            request.setAttribute("players", players);
            request.getRequestDispatcher("WEB-INF/pages/admin.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    /**
     * Handles POST requests for admin operations like add, update, and delete.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException if server-side error occurs
     * @throws IOException if client-side error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "add":
                    handleAddPlayer(request, response);
                    break;
                case "update":
                    handleUpdatePlayer(request, response);
                    break;
                case "delete":
                    handleDeletePlayer(request, response);
                    break;
                default:
                    response.sendRedirect("admin");
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    /**
     * Handles logic for adding a new player.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException if validation or database error occurs
     * @throws IOException if request forwarding fails
     * @throws SQLException if database interaction fails
     */
    private void handleAddPlayer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String runsStr = request.getParameter("runs");

        // Validate phone number
        if (!isValidPhone(phone)) {
            request.setAttribute("error", "Phone number must start with 98 and be 10 digits long");
            doGet(request, response);
            return;
        }

        // Validate runs input
        if (!isValidInteger(runsStr)) {
            request.setAttribute("error", "Runs must be a valid number");
            doGet(request, response);
            return;
        }

        int runs = Integer.parseInt(runsStr);
        Player newPlayer = new Player(name, email, phone, runs);

        // Attempt to add player
        if (playerService.addPlayer(newPlayer)) {
            request.setAttribute("success", "Player added successfully");
        } else {
            request.setAttribute("error", "Failed to add player. Email might already exist.");
        }

        doGet(request, response);
    }

    /**
     * Handles logic for updating an existing player's information.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException if validation or database error occurs
     * @throws IOException if request forwarding fails
     * @throws SQLException if database interaction fails
     */
    private void handleUpdatePlayer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String originalEmail = request.getParameter("originalEmail");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String runsStr = request.getParameter("runs");

        // Validate phone number
        if (!isValidPhone(phone)) {
            request.setAttribute("error", "Phone number must start with 98 and be 10 digits long");
            doGet(request, response);
            return;
        }

        // Validate runs input
        if (!isValidInteger(runsStr)) {
            request.setAttribute("error", "Runs must be a valid number");
            doGet(request, response);
            return;
        }

        int runs = Integer.parseInt(runsStr);
        Player updatedPlayer = new Player(name, email, phone, runs);

        // Attempt to update player
        if (playerService.updatePlayer(originalEmail, updatedPlayer)) {
            request.setAttribute("success", "Player updated successfully");
        } else {
            request.setAttribute("error", "Failed to update player. Player might not exist.");
        }

        doGet(request, response);
    }

    /**
     * Handles logic for deleting a player.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException if database error occurs
     * @throws IOException if request forwarding fails
     * @throws SQLException if database interaction fails
     */
    private void handleDeletePlayer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String email = request.getParameter("email");

        // Attempt to delete player
        if (playerService.deletePlayer(email)) {
            request.setAttribute("success", "Player deleted successfully");
        } else {
            request.setAttribute("error", "Failed to delete player. Player might not exist.");
        }

        doGet(request, response);
    }

    /**
     * Validates phone number format.
     *
     * @param phone the phone number string
     * @return true if valid, false otherwise
     */
    private boolean isValidPhone(String phone) {
        return phone != null && phone.matches("98\\d{8}") && phone.length() == 10;
    }

    /**
     * Validates if the input string is a valid integer.
     *
     * @param input the input string
     * @return true if valid integer, false otherwise
     */
    private boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Cleans up resources and closes the database connection when the servlet is destroyed.
     */
    @Override
    public void destroy() {
        super.destroy();
        if (playerService != null) {
            try {
                playerService.closeConnection();
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }
}
