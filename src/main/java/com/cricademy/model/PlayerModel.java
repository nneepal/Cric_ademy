package com.cricademy.model;

public class PlayerModel {
    private String username;
    private String email;
    private String password;
    private String phone;
    private String role;
    private String profileImage;
    
    public PlayerModel() {}

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

    public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
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
