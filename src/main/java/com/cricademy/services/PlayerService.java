package com.cricademy.services;

import com.cricademy.model.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PlayerService provides CRUD operations for Player entities.
 * It interacts with the database using the provided JDBC Connection.
 * 
 * Author: Arpan Nepal
 * LMUID: 23048647
 */
public class PlayerService {
    private Connection connection;

    /**
     * Constructor that initializes PlayerService with a database connection.
     *
     * @param connection a valid JDBC connection object
     */
    public PlayerService(Connection connection) {
        this.connection = connection;
    }

    /**
     * Adds a new player to the database.
     *
     * @param player the Player object to add
     * @return true if insertion was successful, false otherwise
     * @throws SQLException if a database access error occurs
     */
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

    /**
     * Retrieves all players from the database.
     *
     * @return a list of Player objects
     * @throws SQLException if a database access error occurs
     */
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

    /**
     * Updates an existing player's details in the database.
     *
     * @param email the email of the player to update
     * @param updatedPlayer a Player object containing updated values
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database access error occurs
     */
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

    /**
     * Deletes a player from the database based on email.
     *
     * @param email the email of the player to delete
     * @return true if deletion was successful, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean deletePlayer(String email) throws SQLException {
        String query = "DELETE FROM players WHERE player_email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Retrieves a player from the database by email.
     *
     * @param email the email of the player to retrieve
     * @return a Player object if found, null otherwise
     * @throws SQLException if a database access error occurs
     */
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

    /**
     * Searches for players whose names contain the given string (case-insensitive).
     *
     * @param name the substring to search for in player names
     * @return a list of Player objects matching the search criteria
     * @throws SQLException if a database access error occurs
     */
    public List<Player> searchPlayersByName(String name) throws SQLException {
        List<Player> players = new ArrayList<>();
        String query = "SELECT * FROM players WHERE player_name LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
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
        }
        return players;
    }

    /**
     * Gets the highest runs scored by any player.
     *
     * @return the maximum runs value
     * @throws SQLException if a database access error occurs
     */
    public int getHighestRuns() throws SQLException {
        String query = "SELECT MAX(player_runs) AS max_runs FROM players";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("max_runs");
            }
        }
        return 0;
    }

    /**
     * Gets the total number of players in the system.
     *
     * @return the count of players
     * @throws SQLException if a database access error occurs
     */
    public int getPlayerCount() throws SQLException {
        String query = "SELECT COUNT(*) AS player_count FROM players";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("player_count");
            }
        }
        return 0;
    }

    /**
     * Gets the lowest runs scored by any player.
     *
     * @return the minimum runs value
     * @throws SQLException if a database access error occurs
     */
    public int getLowestRuns() throws SQLException {
        String query = "SELECT MIN(player_runs) AS min_runs FROM players";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("min_runs");
            }
        }
        return 0;
    }

    /**
     * Gets the total runs scored by all players combined.
     *
     * @return the sum of all players' runs
     * @throws SQLException if a database access error occurs
     */
    public int getTotalRuns() throws SQLException {
        String query = "SELECT SUM(player_runs) AS total_runs FROM players";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("total_runs");
            }
        }
        return 0;
    }

    /**
     * Gets the number of users with the "player" role.
     * Note: This assumes you have a users table with a role column.
     *
     * @return the count of users with player role
     * @throws SQLException if a database access error occurs
     */
    public int getPlayerRoleCount() throws SQLException {
        String query = "SELECT COUNT(*) AS player_role_count FROM users WHERE role = 'player'";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("player_role_count");
            }
        }
        return 0;
    }

    /**
     * Closes the database connection held by this service.
     *
     * @throws SQLException if an error occurs while closing the connection
     */
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}