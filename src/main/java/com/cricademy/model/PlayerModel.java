package com.cricademy.model;

public class PlayerModel {
    private String username;
    private String email;
    private String password;
    private String phone;
    private String role;
    private String profileImage;

    public PlayerModel(String username, String email, String password, String phone, String role, String profileImage) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
