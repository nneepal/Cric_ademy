package com.cricademy.services;

import com.cricademy.config.DbConfig;
import com.cricademy.model.PlayerModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Service class for managing user profile-related operations such as fetching,
 * updating user details, and validating phone and email uniqueness.
 */
public class ProfileService {
    private Connection dbConn;

    public ProfileService() {
        try {
            this.dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("ProfileService: Database connection error: " + e.getMessage());
        }
    }

    /**
     * Fetches user details by their username.
     * 
     * @param username The username of the user whose details are to be fetched.
     * @return The PlayerModel object containing user details if found, or null if not.
     */
    public PlayerModel getUserDetails(String username) {
        if (dbConn == null) {
            System.err.println("DB connection is null in ProfileService.");
            return null;
        }

        String query = "SELECT * FROM Users WHERE username = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                PlayerModel user = new PlayerModel();
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                user.setProfileImage(rs.getString("Profile_Image"));
                return user;
            }

        } catch (SQLException e) {
            System.err.println("Error fetching user details: " + e.getMessage());
        }

        return null;
    }
}