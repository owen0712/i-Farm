package com.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/ifarm";
    private String username = "root";
    private String password = "root";
    private Connection connection;

    public DAO() {
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
