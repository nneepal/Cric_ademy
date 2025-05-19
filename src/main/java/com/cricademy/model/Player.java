package com.cricademy.model;

/**
 * Player model class represents a cricket player with basic details.
 * It contains player name, email, phone number, and the number of runs scored.
 * 
 * Author: Arpan Nepal
 * LMUID: 23048647
 */
public class Player {
    // Player's full name
    private String playerName;

    // Player's email address
    private String playerEmail;

    // Player's phone number
    private String phone;

    // Total runs scored by the player
    private int playerRuns;

    /**
     * Default constructor.
     * Creates an empty Player object.
     */
    public Player() {}

    /**
     * Parameterized constructor to create a Player object with details.
     * 
     * @param playerName  the player's full name
     * @param playerEmail the player's email address
     * @param phone       the player's phone number
     * @param playerRuns  total runs scored by the player
     */
    public Player(String playerName, String playerEmail, String phone, int playerRuns) {
        this.playerName = playerName;
        this.playerEmail = playerEmail;
        this.phone = phone;
        this.playerRuns = playerRuns;
    }

    /**
     * Gets the player's name.
     * 
     * @return player's full name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the player's name.
     * 
     * @param playerName the player's full name to set
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Gets the player's email address.
     * 
     * @return player's email
     */
    public String getPlayerEmail() {
        return playerEmail;
    }

    /**
     * Sets the player's email address.
     * 
     * @param playerEmail the player's email to set
     */
    public void setPlayerEmail(String playerEmail) {
        this.playerEmail = playerEmail;
    }

    /**
     * Gets the player's phone number.
     * 
     * @return player's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the player's phone number.
     * 
     * @param phone the player's phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the total runs scored by the player.
     * 
     * @return player's total runs
     */
    public int getPlayerRuns() {
        return playerRuns;
    }

    /**
     * Sets the total runs scored by the player.
     * 
     * @param playerRuns the number of runs to set
     */
    public void setPlayerRuns(int playerRuns) {
        this.playerRuns = playerRuns;
    }
}
