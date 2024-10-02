package com.example.courseproject.dao;
import com.example.courseproject.config.DbUtils;
import com.example.courseproject.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements DAO<Product> {


    public static ProductDAO getInstance() {
        return new ProductDAO();
    }
    @Override
    public List<Product> getAll() {
        long startTime = System.currentTimeMillis();
        List<Product> products = new ArrayList<>();
        String query = "SELECT product_id, batch_id, product_name, selling_price, image_url, quantity FROM Products";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getInt("batch_id"),
                        rs.getString("product_name"),
                        rs.getDouble("selling_price"),
                        rs.getInt("quantity"),
                        rs.getString("image_url")

                );

                products.add(product);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.printf("Query executed in: %d ms%n", duration);
        return products;
    }

    @Override
    public List<Product> getById(int id) {
        long startTime = System.currentTimeMillis();
        List<Product> products = new ArrayList<>();
        String query = "SELECT product_id, batch_id, product_name, selling_price, image_url, quantity FROM Products WHERE product_id = ?";

        try(Connection conn = DbUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, id);
        try( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                            product.setProductId(rs.getInt("product_id"));
                            product.setBatchId(rs.getInt("batch_id"));
                            product.setProductName(rs.getString("product_name"));
                            product.setSellingPrice(rs.getDouble("selling_price"));
                            product.setImageUrl(rs.getString("image_url"));
                            product.setQuantity(rs.getInt("quantity"));
                    products.add(product);
                }
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        long endTime = System.currentTimeMillis(); // End timing

        long duration = endTime - startTime; // Calculate duration

        System.out.println("Query executed in: " + duration + "= ?");
        return products;
    }



    @Override
    public List<Product> getByCondition(String condition) {
        long startTime = System.currentTimeMillis();
        List<Product> products = new ArrayList<>();
        if(condition == null || condition.isEmpty()) {
            return null;
        }
        String query = "SELECT * FROM Products WHERE " + condition;

        try(Connection conn = DbUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)){

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getInt("batch_id"),
                            rs.getString("product_name"),
                            rs.getDouble("selling_price"),
                            rs.getString("image_url"),
                            rs.getInt("quantity"),
                            rs.getTimestamp("expiration_date"),
                            rs.getString("manufacturer")
                    );
                    products.add(product);
                }
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        long endTime = System.currentTimeMillis(); // End timing

        long duration = endTime - startTime; // Calculate duration

        System.out.println("Query executed in: " + duration + "= ?");
        return products;
    }

    @Override
    public void add(Product entity) {
            String query = "INSERT INTO Products (product_id, batch_id, product_name, selling_price, image_url, quantity, expiration_date, manufacturer) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = DbUtils.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, entity.getProductId());
                stmt.setInt(2, entity.getBatchId());
                stmt.setString(3, entity.getProductName());

                // Use BigDecimal for selling_price to avoid precision issues
                stmt.setDouble(4, entity.getSellingPrice());

                stmt.setString(5, entity.getImageUrl());
                stmt.setInt(6, entity.getQuantity());

                // Ensure expiration_date is correctly converted to java.sql.Timestamp
                stmt.setTimestamp(7, entity.getExpirationDate());

                stmt.setString(8, entity.getManufacturer());

                // Execute the query
                stmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage()); // Handle the SQL exception
            }
    }

    @Override
    public void update(Product entity) {
        String query = "UPDATE Product SET batch_id = ?, product_name = ?, selling_price = ?, image_url = ?, quantity = ?, expiration_date = ?, manufacturer = ? " +
                "WHERE product_id = ? AND batch_id = ?";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters for the prepared statement
            stmt.setInt(1, entity.getBatchId());
            stmt.setString(2, entity.getProductName());
            stmt.setDouble(3, entity.getSellingPrice());
            stmt.setString(4, entity.getImageUrl());
            stmt.setInt(5, entity.getQuantity());
            stmt.setTimestamp(6, entity.getExpirationDate());
            stmt.setString(7, entity.getManufacturer());
            stmt.setInt(8, entity.getProductId());
            stmt.setInt(9, entity.getBatchId());

            // Execute the update
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Product updated successfully!");
            } else {
                System.out.println("No product found with the given identifiers.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());  // Handle SQL exception
        }

    }

    @Override
    public void delete(int id) {
    }

    public Product getByIdAndBatch(int productId, int batchId) {

        long startTime = System.currentTimeMillis();
        Product product = new Product();
        String query = "SELECT product_id, batch_id, product_name, selling_price, image_url, quantity, expiration_date, manufacturer " +
                "FROM Products WHERE product_id = ? AND batch_id = ?";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            stmt.setInt(2, batchId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    product = new Product(
                            rs.getInt("product_id"),
                            rs.getInt("batch_id"),
                            rs.getString("product_name"),
                            rs.getDouble("selling_price"),
                            rs.getString("image_url"),
                            rs.getInt("quantity"),
                            rs.getTimestamp("expiration_date"),
                            rs.getString("manufacturer")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        long endTime = System.currentTimeMillis(); // End timing

        long duration = endTime - startTime; // Calculate duration

        System.out.println("Query executed in: " + duration + "= ?");

        return product;
    }


    public void delete(int product_id, int batch_id) {
        String query = "DELETE FROM Products WHERE product_id = ? AND batch_id = ?";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the product_id parameter
            stmt.setInt(1, product_id);
            stmt.setInt(2, batch_id);

            // Execute the delete query
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Product deleted successfully!");
            } else {
                System.out.println("No product found with the given ID.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage()); // Handle SQL exception
        }
    }




}