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

@WebServlet("/admin")
public class Admin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PlayerService playerService;

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            if ("edit".equals(action)) {
                String email = request.getParameter("email");
                Player player = playerService.getPlayerByEmail(email);
                request.setAttribute("playerToEdit", player);
            }

            List<Player> players = playerService.getAllPlayers();
            request.setAttribute("players", players);
            request.getRequestDispatcher("WEB-INF/pages/admin.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

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

    private void handleAddPlayer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String runsStr = request.getParameter("runs");

        if (!isValidPhone(phone)) {
            request.setAttribute("error", "Phone number must start with 98 and be 10 digits long");
            doGet(request, response);
            return;
        }

        if (!isValidInteger(runsStr)) {
            request.setAttribute("error", "Runs must be a valid number");
            doGet(request, response);
            return;
        }

        int runs = Integer.parseInt(runsStr);
        Player newPlayer = new Player(name, email, phone, runs);

        if (playerService.addPlayer(newPlayer)) {
            request.setAttribute("success", "Player added successfully");
        } else {
            request.setAttribute("error", "Failed to add player. Email might already exist.");
        }

        doGet(request, response);
    }

    private void handleUpdatePlayer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String originalEmail = request.getParameter("originalEmail");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String runsStr = request.getParameter("runs");

        if (!isValidPhone(phone)) {
            request.setAttribute("error", "Phone number must start with 98 and be 10 digits long");
            doGet(request, response);
            return;
        }

        if (!isValidInteger(runsStr)) {
            request.setAttribute("error", "Runs must be a valid number");
            doGet(request, response);
            return;
        }

        int runs = Integer.parseInt(runsStr);
        Player updatedPlayer = new Player(name, email, phone, runs);

        if (playerService.updatePlayer(originalEmail, updatedPlayer)) {
            request.setAttribute("success", "Player updated successfully");
        } else {
            request.setAttribute("error", "Failed to update player. Player might not exist.");
        }

        doGet(request, response);
    }

    private void handleDeletePlayer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String email = request.getParameter("email");

        if (playerService.deletePlayer(email)) {
            request.setAttribute("success", "Player deleted successfully");
        } else {
            request.setAttribute("error", "Failed to delete player. Player might not exist.");
        }

        doGet(request, response);
    }

    private boolean isValidPhone(String phone) {
        return phone != null && phone.matches("98\\d{8}") && phone.length() == 10;
    }

    private boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

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
