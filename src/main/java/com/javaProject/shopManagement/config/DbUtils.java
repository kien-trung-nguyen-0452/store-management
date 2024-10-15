package com.javaProject.shopManagement.config;


import com.javaProject.shopManagement.exception.GlobalExeptionHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            String url = "jdbc:mysql://localhost:3306/warehouse";
            String user = "root";
            String password = "123456789";
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connection Established!");
            }
        }
        catch (SQLException e){
            GlobalExeptionHandler.handleException(e);
            System.out.println("Fail to connecting" + e.getMessage());
        }
        return connection;
    }
}
