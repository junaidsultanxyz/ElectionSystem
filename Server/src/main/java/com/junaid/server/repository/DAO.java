package com.junaid.server.repository;

// @author junaidxyz

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DAO {
    
    public void ExampleFunction(int id) {
        String procedure = "{CALL procedureName(?)}";

        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(procedure)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Username: " + rs.getString("username"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
