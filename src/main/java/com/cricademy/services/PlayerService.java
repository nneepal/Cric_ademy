package com.cricademy.services;

import com.cricademy.model.Player;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerService {
    private Connection connection;
    
    public PlayerService(Connection connection) {
        this.connection = connection;
    }
    
    // Create a new player
    public boolean addPlayer(Player player) throws SQLException {
        String query = "INSERT INTO players (player_name, player_email, phone, player_runs) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, player.getPlayerName());
            stmt.setString(2, player.getPlayerEmail());
            stmt.setString(3, player.getPhone());
            stmt.setInt(4, player.getPlayerRuns());
            return stmt.executeUpdate() > 0;
        }
    }
    
    // Read all players
    public List<Player> getAllPlayers() throws SQLException {
        List<Player> players = new ArrayList<>();
        String query = "SELECT * FROM players";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Player player = new Player(
                    rs.getString("player_name"),
                    rs.getString("player_email"),
                    rs.getString("phone"),
                    rs.getInt("player_runs")
                );
                players.add(player);
            }
        }
        return players;
    }
    
    // Update player
    public boolean updatePlayer(String email, Player updatedPlayer) throws SQLException {
        String query = "UPDATE players SET player_name = ?, phone = ?, player_runs = ? WHERE player_email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, updatedPlayer.getPlayerName());
            stmt.setString(2, updatedPlayer.getPhone());
            stmt.setInt(3, updatedPlayer.getPlayerRuns());
            stmt.setString(4, email);
            return stmt.executeUpdate() > 0;
        }
    }
    
    // Delete player
    public boolean deletePlayer(String email) throws SQLException {
        String query = "DELETE FROM players WHERE player_email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            return stmt.executeUpdate() > 0;
        }
    }
    
    // Get player by email
    public Player getPlayerByEmail(String email) throws SQLException {
        String query = "SELECT * FROM players WHERE player_email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Player(
                        rs.getString("player_name"),
                        rs.getString("player_email"),
                        rs.getString("phone"),
                        rs.getInt("player_runs")
                    );
                }
            }
        }
        return null;
    }
    
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
