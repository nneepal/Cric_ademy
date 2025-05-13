package com.cricademy.services;

import com.cricademy.config.DbConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginService {

    // Validate username and password, and return role if valid
    public String authenticate(String username, String password) {
        String role = null;
        String sql = "SELECT password, role FROM Users WHERE username = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String encryptedPassword = rs.getString("password");
                String decryptedPassword = com.cricademy.util.PasswordUtil.decrypt(encryptedPassword, username);

                if (decryptedPassword != null && decryptedPassword.equals(password)) {
                    role = rs.getString("role"); // Set role only if password matches
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle properly in production
        }

        return role; // returns admin/player if login success, null if fail
    }
}