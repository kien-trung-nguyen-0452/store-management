package com.javaProject.shopManagement.dao.implementation;

import com.javaProject.shopManagement.config.DbUtils;
import com.javaProject.shopManagement.dao.interfaces.ProductStatisticDAO;
import com.javaProject.shopManagement.exception.GlobalExceptionHandler;
import com.javaProject.shopManagement.model.ProductStatistic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductStatisticDAOImpl implements ProductStatisticDAO {
    @Override
    public List<ProductStatistic> getStatisticsByRange(Timestamp startDate, Timestamp endDate) {
            List<ProductStatistic> statisticsList = new ArrayList<>();
            String query = """
                    SELECT
                        p.product_id,
                        p.product_name,
                        p.image_url,
                        SUM(s.quantity) AS total_quantity_sold
                    FROM\s
                        invoice i
                    JOIN\s
                        sales s ON i.invoice_code = s.invoice_code
                    JOIN\s
                        product p ON p.product_id = s.product_id
                    WHERE\s
                        i.invoice_date BETWEEN ? AND ?
                    GROUP BY\s
                        p.product_id, p.product_name
                    ORDER BY\s
                        total_quantity_sold DESC;
                    """;

            try (Connection connection = DbUtils.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setTimestamp(1, startDate);
                statement.setTimestamp(2, endDate);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int productId = resultSet.getInt("product_id");
                        String productName = resultSet.getString("product_name");
                        int totalQuantitySold = resultSet.getInt("total_quantity_sold");
                        String imageUrl = resultSet.getString("image_url");
                        ProductStatistic stats = new ProductStatistic(productId, productName, totalQuantitySold, imageUrl);
                        statisticsList.add(stats);
                    }
                }
            } catch (SQLException e) {
                GlobalExceptionHandler.handleException(e);
            }

            return statisticsList;
        }
    }
