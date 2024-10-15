package com.javaProject.shopManagement.dao;

import com.javaProject.shopManagement.config.DbUtils;
import com.javaProject.shopManagement.exception.GlobalExeptionHandler;
import com.javaProject.shopManagement.models.Batch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BatchDAOImpl implements DAO<Batch> {

    public static BatchDAOImpl getInstance() {
        return new BatchDAOImpl();
    }

    @Override
    public List<Batch> getAll() {
        long startTime = System.currentTimeMillis();
        List<Batch> batches = new ArrayList<>();
        String query = "SELECT batch_id, product_id, product_name, arrival_date, quantity, purchase_price, supplier " +
                "FROM bacth";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {


            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Batch batch = new Batch(
                            rs.getInt("batch_id"),
                            rs.getString("supplier"),
                            rs.getTimestamp("arrival_date"),
                            rs.getInt("quantity"),
                            rs.getDouble("purchase_price"),
                            rs.getString("product_name"),
                            rs.getInt("product_id")
                    );

                    batches.add(batch);
                }

            }
        }catch (SQLException e) {
            GlobalExeptionHandler.handleException(e);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.printf("Query executed in: %d ms%n", duration);
        return batches;
    }

    @Override
    public List<Batch> getById(int batchId) {
        long startTime = System.currentTimeMillis();

        List<Batch> batches = new ArrayList<>();
        String query = "SELECT batch_id, product_id, product_name, arrival_date, quantity, purchase_price, supplier " +
                "FROM bacth WHERE batch_id = ?";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, batchId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Batch batch = new Batch(
                            rs.getInt("batch_id"),
                            rs.getString("supplier"),
                            rs.getTimestamp("arrival_date"),
                            rs.getInt("quantity"),
                            rs.getDouble("purchase_price"),
                            rs.getString("product_name"),
                            rs.getInt("product_id")
                    );
                    batches.add(batch);
                }
            }

        } catch (SQLException e) {
           GlobalExeptionHandler.handleException(e);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Query executed in: " + duration + " ms");
        return batches;
    }


    @Override
    public List<Batch> getByCondition(String condition) {
            long startTime = System.currentTimeMillis();
            List<Batch> batches = new ArrayList<>();
            if (condition == null || condition.isEmpty()) {
                return null; // or throw IllegalArgumentException
            }
            String query = "SELECT batch_id, product_id, product_name, arrival_date, quantity, purchase_price, supplier " +
                    "FROM bacth WHERE " + condition;

            try (Connection conn = DbUtils.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                try (ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {
                        Batch batch = new Batch(
                                rs.getInt("batch_id"),
                                rs.getString("supplier"),
                                rs.getTimestamp("arrival_date"),
                                rs.getInt("quantity"),
                                rs.getDouble("purchase_price"),
                                rs.getString("product_name"),
                                rs.getInt("product_id")
                        );
                        batches.add(batch);
                    }

                }
            }catch (SQLException e) {
                GlobalExeptionHandler.handleException(e);
            }

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            System.out.println("Query executed in: " + duration + " ms");
            return batches;

    }



    @Override
    public void add(Batch entity) {
        String query = "INSERT INTO Batch (batch_id, product_id, product_name, arrival_date, quantity, purchase_price, supplier) " +
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

            GlobalExeptionHandler.handleException(e);
        }
    }

    @Override
    public void update(Batch entity) {
        String query = "UPDATE Batch SET product_id = ?, product_name = ?, arrival_date = ?, quantity = ?, purchase_price = ?, supplier = ? " +
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
           GlobalExeptionHandler.handleException(e);
        }
    }

    @Override
    public void delete(int id) {

    }

    public void delete(int batchId, int productId) {
        String query = "DELETE FROM bacth WHERE batch_id = ? AND product_id = ?";

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
           GlobalExeptionHandler.handleException(e);
        }
    }
}
