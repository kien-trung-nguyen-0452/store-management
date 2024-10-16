package com.javaProject.shopManagement.dao.implementation;

import com.javaProject.shopManagement.config.DbUtils;
import com.javaProject.shopManagement.dao.interfaces.SalesDAO;
import com.javaProject.shopManagement.exception.GlobalExceptionHandler;
import com.javaProject.shopManagement.entity.Sales;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesDAOImpl implements SalesDAO {

    public static SalesDAOImpl getInstance() {
        return new SalesDAOImpl();
    }

    @Override
    public List<Sales> getAll() {
        List<Sales> salesList = new ArrayList<>();
        String query = "SELECT invoice_code, product_id, batch_id, quantity, unit_price, total_amount FROM sales";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Sales sales = new Sales();
                readAllFromResulSet(rs, sales);
                salesList.add(sales);
            }

        } catch (SQLException e) {
            GlobalExceptionHandler.handleException(e);
        }
        return salesList;
    }

    @Override
    public List<Sales> getById(int invoice_code) {
        List<Sales> salesList = new ArrayList<>();
        String query = "SELECT invoice_code, product_id, batch_id, quantity, unit_price, total_amount FROM sales WHERE invoice_code = ?";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, invoice_code);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Sales sales = new Sales();
                    readAllFromResulSet(rs, sales);
                    salesList.add(sales);
                }
            }

        } catch (SQLException e) {
            GlobalExceptionHandler.handleException(e);
        }
        return salesList;
    }

    @Override
    public List<Sales> getByCondition(String condition) {
            List<Sales> salesList = new ArrayList<>();
            if (condition == null || condition.isEmpty()) {
                System.out.println("Condition cannot be null or empty");
                return null;
            }
            String query = "SELECT invoice_code, product_id, batch_id, quantity, unit_price, total_amount FROM sales WHERE " + condition;

            try (Connection conn = DbUtils.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)){
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Sales sales = new Sales();
                        readAllFromResulSet(rs, sales);
                        salesList.add(sales);
                    }
                }
            }catch (SQLException e) {
                GlobalExceptionHandler.handleException(e);
            }

            return salesList;
    }

    @Override
    public void add(Sales entity) {
        String query = "INSERT INTO sales (invoice_code, product_id, batch_id, quantity, unit_price, total_amount) " +
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
            GlobalExceptionHandler.handleException(e);
        }
    }

    /*@Override
    public void update(Sales entity) {
        String query = "UPDATE sales SET quantity = ?, unit_price = ?, total_amount = ? WHERE invoice_code = ? AND product_id = ? AND batch_id = ?";

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
            GlobalExeptionHandler.handleException(e);
        }
    }*/

    @Override
    public void delete(int invoice_code) {
        String query = "DELETE FROM sales WHERE invoice_code = ?";

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
            GlobalExceptionHandler.handleException(e);
        }
    }

    private void readAllFromResulSet (ResultSet rs, Sales sales) throws SQLException {
        sales.setInvoiceId(rs.getInt("invoice_code"));
        sales.setBatchId(rs.getInt("batch_id"));
        sales.setProductId(rs.getInt("product_id"));
        sales.setProductName(rs.getString("product_name"));
        sales.setQuantity(rs.getInt("quantity"));
        sales.setPrice(rs.getDouble("unit_price"));
        sales.setTotalAmount(rs.getDouble("total_amount"));
    }
}