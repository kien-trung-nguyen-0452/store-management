package com.javaProject.shopManagement.dao.implementation;

import com.javaProject.shopManagement.config.DbUtils;
import com.javaProject.shopManagement.dao.interfaces.ProductStatisticDAO;
import com.javaProject.shopManagement.exception.GlobalExceptionHandler;
import com.javaProject.shopManagement.dto.ProductStatisticDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductStatisticDAOImpl implements ProductStatisticDAO {

    public static ProductStatisticDAOImpl getInstance(){
        return new ProductStatisticDAOImpl();
    }
    @Override
    public List<ProductStatisticDTO> getStatisticsByRange(Timestamp startDate, Timestamp endDate) {
        List<ProductStatisticDTO> statisticsList = new ArrayList<>();
        String query = """
            SELECT 
                s.product_id, 
                p.product_name,
                s.batch_id,
                p.image_url,
                SUM(s.quantity) AS total_quantity_sold
            FROM 
                sales s
            JOIN 
                invoice i ON i.invoice_code = s.invoice_code
            JOIN 
                product p ON s.product_id = p.product_id
            WHERE 
                i.invoice_date BETWEEN ? AND ?
            GROUP BY 
                s.product_id, p.product_name, s.batch_id, p.image_url
            """;

        try (Connection connection = DbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setTimestamp(1, startDate);
            statement.setTimestamp(2, endDate);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int productId = resultSet.getInt("product_id");
                    int batchId = resultSet.getInt("batch_id");
                    String productName = resultSet.getString("product_name");
                    int totalQuantitySold = resultSet.getInt("total_quantity_sold");
                    String imageUrl = resultSet.getString("image_url");


                    ProductStatisticDTO stats = new ProductStatisticDTO(productId, productName, totalQuantitySold, batchId, imageUrl);
                    statisticsList.add(stats);
                }
            }
        } catch (SQLException e) {
            GlobalExceptionHandler.handleException(e);
        }

        return statisticsList;
    }
    }
