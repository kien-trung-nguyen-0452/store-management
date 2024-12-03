package com.javaProject.shopManagement.dao.implementation;

import com.javaProject.shopManagement.config.DbUtils;
import com.javaProject.shopManagement.dao.interfaces.RevenueStatisticDAO;
import com.javaProject.shopManagement.dto.RevenueStatisticDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class RevenueStatisticDAOImpl implements RevenueStatisticDAO {
    public static RevenueStatisticDAOImpl getInstance(){
        return new RevenueStatisticDAOImpl();
    }
    @Override
    public RevenueStatisticDTO getRevenueStatisticOfDay(Timestamp start, Timestamp end) {
       double totalRevenue = getTotalRevenue(start, end);
       double totalCost = getTotalCost(start, end);
       return new RevenueStatisticDTO(totalRevenue, totalCost);
    }

    private double getTotalRevenue(Timestamp start, Timestamp end) {
        String query = """
                SELECT
                    SUM(i.total_revenue) AS total_revenue
                FROM
                    invoice i
                WHERE (i.invoice_date) BETWEEN ? AND ?
                GROUP BY
                    DATE(i.invoice_date);
                """;
        try(Connection conn = DbUtils.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setTimestamp(1, start);
            preparedStatement.setTimestamp(2, end);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getDouble("total_revenue");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }

    private double getTotalCost(Timestamp start, Timestamp end) {
        String query = """
                    SELECT
                         SUM(s.quantity * b.purchase_price) AS total_cost
                     FROM
                         sales s
                     JOIN
                         invoice i ON s.invoice_code = i.invoice_code
                     JOIN
                         batch b ON s.product_id = b.product_id AND s.batch_id = b.batch_id
                     WHERE
                         DATE(i.invoice_date) BETWEEN ? AND ?
                     GROUP BY
                         DATE(i.invoice_date);
                """;
        try(Connection conn = DbUtils.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setTimestamp(1, start);
            preparedStatement.setTimestamp(2, end);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getDouble("total_cost");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;

    }
}
