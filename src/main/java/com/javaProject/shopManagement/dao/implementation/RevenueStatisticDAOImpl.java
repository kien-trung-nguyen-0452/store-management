package com.javaProject.shopManagement.dao.implementation;

import com.javaProject.shopManagement.config.DbUtils;
import com.javaProject.shopManagement.dao.interfaces.RevenueStatisticDAO;
import com.javaProject.shopManagement.dto.RevenueStatisticDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RevenueStatisticDAOImpl implements RevenueStatisticDAO {
    public static RevenueStatisticDAOImpl getInstance() {
        return new RevenueStatisticDAOImpl();
    }

    @Override
    public List<RevenueStatisticDTO> getRevenueStatisticsByDateRange(Timestamp start, Timestamp end) {
        String query = """
            
                SELECT
                    DATE(i.invoice_date) AS date,
                    SUM(i.total_revenue) AS total_revenue,
                     SUM(s.quantity * b.purchase_price) AS total_cost
                FROM
                    invoice i
                LEFT JOIN
                    sales s ON i.invoice_code = s.invoice_code
                LEFT JOIN
                     batch b ON s.product_id = b.product_id
                AND s.batch_id = b.batch_id
                WHERE
                      i.invoice_date BETWEEN ? AND ?
                GROUP BY
                DATE(i.invoice_date)
                ORDER BY DATE(i.invoice_date);
            """;

        List<RevenueStatisticDTO> revenueStatistics = new ArrayList<>();

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setTimestamp(1, start);
            preparedStatement.setTimestamp(2, end);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                double totalRevenue = resultSet.getDouble("total_revenue");
                double totalCost = resultSet.getDouble("total_cost");
                String date = resultSet.getString("date");

                RevenueStatisticDTO statistic = new RevenueStatisticDTO(totalRevenue, totalCost, date);
                revenueStatistics.add(statistic);
            }
        } catch (

    Exception e) {
            e.printStackTrace();
        }

        return revenueStatistics;
   }
}