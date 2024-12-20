package com.javaProject.shopManagement.dao.implementation;

import com.javaProject.shopManagement.config.DbUtils;
import com.javaProject.shopManagement.dao.interfaces.BatchDAO;
import com.javaProject.shopManagement.exception.GlobalExceptionHandler;
import com.javaProject.shopManagement.entity.Batch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.javaProject.shopManagement.exception.DatabaseConnectionException;
import com.javaProject.shopManagement.exception.QueryExecutionException;
import com.javaProject.shopManagement.exception.DataNotFoundException;

public class BatchDAOImpl implements BatchDAO {

    public static BatchDAOImpl getInstance() {
        return new BatchDAOImpl();
    }

    @Override
    public List<Batch> getAll() {
        long startTime = System.currentTimeMillis();
        List<Batch> batches = new ArrayList<>();
        String query = "SELECT batch_id, product_id, product_name, arrival_date, quantity, purchase_price, supplier FROM batch";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Batch batch = new Batch();
                    readAllFromResultSet(rs, batch);
                    batches.add(batch);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to connect to the database.", e);
        } catch (RuntimeException e) {
            throw new QueryExecutionException("Error executing query: " + query, e);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.printf("Query executed in: %d ms%n", duration);

        if (batches.isEmpty()) {
            throw new DataNotFoundException("No batches found.");
        }

        return batches;
    }




    @Override
    public List<Batch> getById(int batchId) {
        long startTime = System.currentTimeMillis();

        List<Batch> batches = new ArrayList<>();
        String query = "SELECT batch_id, product_id, product_name, arrival_date, quantity, purchase_price, supplier " +
                "FROM batch WHERE batch_id = ?";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, batchId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Batch batch = new Batch();
                    readAllFromResultSet(rs, batch);
                    batches.add(batch);
                }
            }

        } catch (SQLException e) {
           GlobalExceptionHandler.handleException(e);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Query executed in: " + duration + " ms" + "number of result: " + batches.size());
        return batches;
    }


    @Override
    public List<Batch> getByCondition(String condition) {
            long startTime = System.currentTimeMillis();
            List<Batch> batches = new ArrayList<>();
            if (condition == null || condition.isEmpty()) {
                return null;
            }
            String query = "SELECT batch_id, product_id, product_name, arrival_date, quantity, purchase_price, supplier " +
                    "FROM batch WHERE " + condition;

            try (Connection conn = DbUtils.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Batch batch = new Batch();
                        readAllFromResultSet(rs, batch);
                        batches.add(batch);
                    }
                }
            }catch (SQLException e) {
                GlobalExceptionHandler.handleException(e);
            }

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            System.out.println("Query executed in: " + duration + " ms");
            return batches;

    }



    @Override
    public void add(Batch entity) {
        String query = "INSERT INTO batch (batch_id, product_id, product_name, arrival_date, quantity, purchase_price, supplier) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, entity.getBatchId());
            stmt.setInt(2, entity.getProductId());
            stmt.setString(3, entity.getProductName());
            stmt.setTimestamp(4, entity.getCreateDate());
            stmt.setInt(5, entity.getQuantity());
            stmt.setDouble(6, entity.getPurchasePrice());
            stmt.setString(7, entity.getSupplierName());

            stmt.executeUpdate();

        } catch (SQLException e) {

            GlobalExceptionHandler.handleException(e);
        }
    }

    @Override
    public void update(Batch entity) {
        String query = "UPDATE batch SET product_id = ?, product_name = ?, arrival_date = ?, quantity = ?, purchase_price = ?, supplier = ? " +
                "WHERE batch_id = ? AND product_id = ?";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, entity.getProductId());
            stmt.setString(2, entity.getProductName());
            stmt.setTimestamp(3, entity.getCreateDate());
            stmt.setInt(4, entity.getQuantity());
            stmt.setDouble(5, entity.getPurchasePrice());
            stmt.setString(6, entity.getSupplierName());
            stmt.setInt(7, entity.getBatchId());
            stmt.setInt(8, entity.getProductId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Batch updated successfully!");
            } else {
                System.out.println("No batch found with the given ID.");
            }

        } catch (SQLException e) {
           GlobalExceptionHandler.handleException(e);
        }
    }

    public void delete(int batchId, int productId) {
        String query = "DELETE FROM batch WHERE batch_id = ? AND product_id = ?";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, batchId);
            stmt.setInt(2, productId);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Batch deleted successfully!");
            } else {
                System.out.println("No batch found with the given ID.");
            }

        } catch (SQLException e) {
           GlobalExceptionHandler.handleException(e);
        }
    }

    private void readAllFromResultSet(ResultSet rs, Batch batch) throws SQLException {
        batch.setBatchId(rs.getInt("batch_id"));
        batch.setProductId(rs.getInt("product_id"));
        batch.setProductName(rs.getString("product_name"));
        batch.setCreateDate(rs.getTimestamp("arrival_date"));
        batch.setQuantity(rs.getInt("quantity"));
        batch.setPurchasePrice(rs.getDouble("purchase_price"));
        batch.setSupplierName(rs.getString("supplier"));
    }
}
