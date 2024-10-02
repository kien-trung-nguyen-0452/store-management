package com.javaProject.shopManagement.config;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
    private static Connection connection;
    private static String url = "jdbc:mysql://localhost:3306/warehouse";
    private static String user = "root";
    private static String password = "123456789";

    public static Connection getConnection() throws SQLException {
        connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connection Established!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Fail to connecting" + e.getMessage());
        }
        return connection;
    }
}
