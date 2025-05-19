package com.cricademy.model;

/**
 * PlayerModel class represents a user/player in the Cricademy system.
 * It contains user details such as username, email, password, phone, role, and profile image.
 * 
 * Author: Arpan Nepal
 * LMUID: 23048647
 */
public class PlayerModel {
    // Username of the player/user
    private String username;

    // Email address of the player/user
    private String email;

    // Encrypted password of the player/user
    private String password;

    // Contact phone number of the player/user
    private String phone;

    // Role of the user (e.g., "player", "admin")
    private String role;

    // Filename or path to the user's profile image
    private String profileImage;
    
    /**
     * Default constructor.
     * Creates an empty PlayerModel object.
     */
    public PlayerModel() {}

    /**
     * Parameterized constructor to initialize PlayerModel with all fields.
     * 
     * @param username     the username of the player
     * @param email        the email address
     * @param password     the password (should be encrypted)
     * @param phone        the phone number
     * @param role         the role of the player (admin/player)
     * @param profileImage the profile image filename/path
     */
    public PlayerModel(String username, String email, String password, String phone, String role, String profileImage) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.profileImage = profileImage;
    }

    /**
     * Gets the username.
     * 
     * @return username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * 
     * @param username username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email address.
     * 
     * @return player's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     * 
     * @param email email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password.
     * 
     * @return player's password (encrypted)
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * 
     * @param password password to set (should be encrypted)
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the phone number.
     * 
     * @return player's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number.
     * 
     * @param phone phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the role of the user.
     * 
     * @return role of the player (e.g., "admin", "player")
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     * 
     * @param role role to set (e.g., "admin", "player")
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets the profile image filename/path.
     * 
     * @return profile image
     */
    public String getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the profile image filename/path.
     * 
     * @param profileImage profile image filename/path to set
     */
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    /**
     * Returns a string representation of the PlayerModel object.
     * Excludes the password field for security reasons.
     */
    @Override
    public String toString() {
        return "PlayerModel{" +
               "username='" + username + '\'' +
               ", email='" + email + '\'' +
               ", phone='" + phone + '\'' +
               ", role='" + role + '\'' +
               ", profileImage='" + profileImage + '\'' +
               '}';
    }
}
