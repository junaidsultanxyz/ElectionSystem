package com.junaid.server.repository;

// @author junaidxyz

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    private static final int    PORT = 3306;
    private static final String IP   = "localhost";
    private static final String DB   = "election_system";
    
    private static final String URL = "jdbc:mysql://" + IP + ":" + PORT + "/" + DB;
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
