package com.cricademy.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class handles the configuration required to establish a connection
 * to the Cricademy MySQL database.
 */
public class DbConfig {

    // Database name
    private static final String DB_NAME = "Cricademy";

    // JDBC URL for the database
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME;

    // Database credentials
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    /**
     * Returns a connection object to interact with the Cricademy database.
     * Loads the MySQL JDBC driver and attempts to establish a connection.
     *
     * @return Connection object to the Cricademy database
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public static Connection getDbConnection() throws SQLException, ClassNotFoundException {
        // Load MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Establish and return the database connection
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
