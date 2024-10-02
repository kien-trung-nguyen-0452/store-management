package com.example.courseproject.dao;

import com.example.courseproject.config.DbUtils;
import com.example.courseproject.models.Sales;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesDAO implements DAO<Sales> {

    public static SalesDAO getInstance() {
        return new SalesDAO();
    }

    @Override
    public List<Sales> getAll() {
        List<Sales> salesList = new ArrayList<>();
        String query = "SELECT invoice_code, product_id, batch_id, quantity, unit_price, total_amount FROM Sales";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Sales sale = new Sales(
                        rs.getInt("invoice_code"),
                        rs.getInt("batch_id"),
                        rs.getInt("product_id"),
                        "", // Product name can be fetched from the Products table if needed
                        rs.getInt("quantity"),
                        rs.getDouble("unit_price"),
                        rs.getDouble("total_amount")
                );
                salesList.add(sale);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());  // Handle SQL exception
        }
        return salesList;
    }

    @Override
    public List<Sales> getById(int invoice_code) {
        List<Sales> salesList = new ArrayList<>();
        String query = "SELECT invoice_code, product_id, batch_id, quantity, unit_price, total_amount FROM Sales WHERE invoice_code = ?";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, invoice_code);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Sales sales = new Sales(
                            rs.getInt("invoice_code"),
                            rs.getInt("batch_id"),
                            rs.getInt("product_id"),
                            "", // Product name can be fetched from Products table if needed
                            rs.getInt("quantity"),
                            rs.getDouble("unit_price"),
                            rs.getDouble("total_amount")
                    );
                    salesList.add(sales);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());  // Handle SQL exception
        }
        return salesList;
    }

    @Override
    public List<Sales> getByCondition(String condition) {
            List<Sales> salesList = new ArrayList<>();

            // Ensure the condition is valid
            if (condition == null || condition.isEmpty()) {
                System.out.println("Condition cannot be null or empty");
                return null;
            }

            // Build the query with the condition
            String query = "SELECT invoice_code, product_id, batch_id, quantity, unit_price, total_amount FROM Sales WHERE " + condition;

            try (Connection conn = DbUtils.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)){

                try (ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {
                        Sales sale = new Sales(
                                rs.getInt("invoice_code"),
                                rs.getInt("batch_id"),
                                rs.getInt("product_id"),
                                "", // Product name can be fetched from Products table if needed
                                rs.getInt("quantity"),
                                rs.getDouble("unit_price"),
                                rs.getDouble("total_amount")
                        );
                        salesList.add(sale);
                    }

                }
            }catch (SQLException e) {
                System.out.println(e.getMessage());  // Handle SQL exception
            }

            return salesList;
    }

    @Override
    public void add(Sales entity) {
        String query = "INSERT INTO Sales (invoice_code, product_id, batch_id, quantity, unit_price, total_amount) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, entity.getInvoiceId());
            stmt.setInt(2, entity.getProductId());
            stmt.setInt(3, entity.getBatchId());
            stmt.setInt(4, entity.getQuantity());
            stmt.setDouble(5, entity.getPrice());
            stmt.setDouble(6, entity.getTotalAmount());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage()); // Handle SQL exception
        }
    }

    @Override
    public void update(Sales entity) {
        String query = "UPDATE Sales SET quantity = ?, unit_price = ?, total_amount = ? WHERE invoice_code = ? AND product_id = ? AND batch_id = ?";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, entity.getQuantity());
            stmt.setDouble(2, entity.getPrice());
            stmt.setDouble(3, entity.getTotalAmount());
            stmt.setInt(4, entity.getInvoiceId());
            stmt.setInt(5, entity.getProductId());
            stmt.setInt(6, entity.getBatchId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Sales record updated successfully!");
            } else {
                System.out.println("No sales record found with the given identifiers.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());  // Handle SQL exception
        }
    }

    @Override
    public void delete(int invoice_code) {
        String query = "DELETE FROM Sales WHERE invoice_code = ?";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, invoice_code);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Sales record deleted successfully!");
            } else {
                System.out.println("No sales record found with the given ID.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage()); // Handle SQL exception
        }
    }
}