package com.cricademy.services;

import com.cricademy.config.DbConfig;
import com.cricademy.model.PlayerModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ProfileService handles user profile operations including fetching and updating
 * user details, as well as validating uniqueness of phone and email.
 * 
 * Author: Arpan Nepal
 * LMUID: 23048647
 */
public class ProfileService {
    private Connection dbConn;

    /**
     * Default constructor that establishes a database connection.
     * Logs an error message if the connection cannot be established.
     */
    public ProfileService() {
        try {
            this.dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("ProfileService: Database connection error: " + e.getMessage());
        }
    }

    /**
     * Retrieves user details by username.
     * 
     * @param username the username of the user
     * @return PlayerModel object with user details if found; null otherwise
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

    /**
     * Updates user profile information.
     * 
     * @param oldUsername       current username to identify the user record
     * @param newUsername       new username to update
     * @param email             new email
     * @param encryptedPassword new encrypted password
     * @param phone             new phone number
     * @param profileImage      new profile image path
     * @return true if the update was successful, false otherwise
     */
    public boolean updateUserProfile(String oldUsername, String newUsername, String email, String encryptedPassword, String phone, String profileImage) {
        if (dbConn == null) {
            System.err.println("DB connection is null in ProfileService.");
            return false;
        }

        String updateQuery = "UPDATE Users SET username = ?, email = ?, password = ?, phone = ?, Profile_Image = ? WHERE username = ?";

        try (PreparedStatement stmt = dbConn.prepareStatement(updateQuery)) {
            stmt.setString(1, newUsername);
            stmt.setString(2, email);
            stmt.setString(3, encryptedPassword);
            stmt.setString(4, phone);
            stmt.setString(5, profileImage);
            stmt.setString(6, oldUsername);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user profile: " + e.getMessage());
        }

        return false;
    }
}
