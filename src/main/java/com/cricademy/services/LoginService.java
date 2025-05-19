package com.cricademy.services;

import com.cricademy.config.DbConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * LoginService class provides user authentication functionality.
 * It validates the username and password against the database records
 * and returns the user role if authentication succeeds.
 * 
 * Author: Arpan Nepal
 * LMUID: 23048647
 */
public class LoginService {

    /**
     * Authenticates a user based on username and password.
     * 
     * This method retrieves the encrypted password and role for the given username from the database.
     * It then decrypts the password using PasswordUtil and compares it with the input password.
     * If they match, it returns the user's role ("admin" or "player").
     * Otherwise, it returns null indicating authentication failure.
     * 
     * @param username the username input by the user
     * @param password the password input by the user (plain text)
     * @return the role of the user if authentication is successful, or null if it fails
     */
    public String authenticate(String username, String password) {
        String role = null;
        String sql = "SELECT password, role FROM Users WHERE username = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the username parameter in the SQL query
            ps.setString(1, username);

            // Execute the query to fetch password and role
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Get encrypted password from the database
                String encryptedPassword = rs.getString("password");

                // Decrypt password using username as key/seed
                String decryptedPassword = com.cricademy.util.PasswordUtil.decrypt(encryptedPassword, username);

                // Validate decrypted password against the input password
                if (decryptedPassword != null && decryptedPassword.equals(password)) {
                    // Authentication successful, get user role
                    role = rs.getString("role");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // In production, use proper logging and error handling
        }

        // Return role if authentication succeeded, else null
        return role;
    }
}
