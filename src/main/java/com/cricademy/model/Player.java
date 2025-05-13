package com.cricademy.model;

public class Player {
    private String playerName;
    private String playerEmail;
    private String phone;
    private int playerRuns;

    // Constructors
    public Player() {}

    public Player(String playerName, String playerEmail, String phone, int playerRuns) {
        this.playerName = playerName;
        this.playerEmail = playerEmail;
        this.phone = phone;
        this.playerRuns = playerRuns;
    }

    // Getters and Setters
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerEmail() {
        return playerEmail;
    }

    public void setPlayerEmail(String playerEmail) {
        this.playerEmail = playerEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPlayerRuns() {
        return playerRuns;
    }

    public void setPlayerRuns(int playerRuns) {
        this.playerRuns = playerRuns;
    }
}
