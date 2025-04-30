package com.cricademy.services;

import com.cricademy.config.DbConfig;
import com.cricademy.model.PlayerModel;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterService {

    // Method to register the player
    public boolean addUser(PlayerModel player) {
        boolean isInserted = false;

        String sql = "INSERT INTO Users (username, email, password, phone, role, profile_image) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, player.getUsername());
            ps.setString(2, player.getEmail());
            ps.setString(3, player.getPassword());
            ps.setString(4, player.getPhone());
            ps.setString(5, player.getRole());
            ps.setString(6, player.getProfileImage());

            int rows = ps.executeUpdate();
            isInserted = rows > 0;

            System.out.println("Rows inserted: " + rows);

        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
        }

        return isInserted;
    }
}
