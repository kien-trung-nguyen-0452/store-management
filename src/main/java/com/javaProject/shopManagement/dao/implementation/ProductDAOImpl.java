package com.javaProject.shopManagement.dao.implementation;
import com.javaProject.shopManagement.config.DbUtils;
import com.javaProject.shopManagement.dao.interfaces.ProductDAO;
import com.javaProject.shopManagement.exception.GlobalExceptionHandler;
import com.javaProject.shopManagement.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {


    public static ProductDAOImpl getInstance() {
        return new ProductDAOImpl();
    }

    @Override
    public List<Product> getAll() {
        long startTime = System.currentTimeMillis();
        List<Product> products = new ArrayList<>();
        String query = "SELECT product_id, batch_id, product_name, selling_price, image_url, quantity, expiration_date FROM product WHERE quantity>0";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Product product = new Product();
                readInformationFromResultSet(rs, product);
                products.add(product);
            }

        } catch (SQLException e) {
            GlobalExceptionHandler.handleException(e);
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
        String query = "SELECT product_id, batch_id, product_name, selling_price, image_url, quantity, expiration_date FROM product WHERE product_id = ? AND quantity >0";

        try(Connection conn = DbUtils.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, id);
        try( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    readInformationFromResultSet(rs, product);
                    products.add(product);
                }
            }

        }catch (SQLException e){
            GlobalExceptionHandler.handleException(e);
        }
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;

        System.out.println("Query executed in: " + duration + "= ?");
        return products;
    }



    @Override
    public List<Product> getByCondition(String keyword) {
        long startTime = System.currentTimeMillis();
        List<Product> products = new ArrayList<>();

        if (keyword == null || keyword.isEmpty()) {
            return products;
        }

        String query = "SELECT * FROM product WHERE product_name LIKE ? AND quantity >0";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, searchKeyword);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    readInformationFromResultSet(rs, product);
                    products.add(product);
                }
            }

        } catch (SQLException e) {
            GlobalExceptionHandler.handleException(e);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Query executed in: " + duration + " ms");
        return products;
    }

    @Override
    public void add(Product entity) {
            String query = "INSERT INTO product (product_id, batch_id, product_name, selling_price, image_url, quantity, expiration_date, manufacturer) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = DbUtils.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, entity.getProductId());
                stmt.setInt(2, entity.getBatchId());
                stmt.setString(3, entity.getProductName());
                stmt.setDouble(4, entity.getSellingPrice());
                stmt.setString(5, entity.getImageUrl());
                stmt.setInt(6, entity.getQuantity());
                stmt.setTimestamp(7, entity.getExpirationDate());
                stmt.setString(8, entity.getManufacturer());
                stmt.executeUpdate();

            } catch (SQLException e) {
                GlobalExceptionHandler.handleException(e);
            }
    }

    @Override
    public void update(Product entity) {
        String query = "UPDATE product SET product_name = ?, selling_price = ?, image_url = ?, quantity = ?, expiration_date = ?, manufacturer = ? " +
                "WHERE product_id = ? AND batch_id = ?";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, entity.getProductName());
            stmt.setDouble(2, entity.getSellingPrice());
            stmt.setString(3, entity.getImageUrl());
            stmt.setInt(4, entity.getQuantity());
            stmt.setTimestamp(5, entity.getExpirationDate());
            stmt.setString(6, entity.getManufacturer());
            stmt.setInt(7, entity.getProductId());
            stmt.setInt(8, entity.getBatchId());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Update successful!");
            }
            else {
                System.out.println("Update failed!");
            }
            System.out.println(entity.toString());
        } catch (SQLException e) {
            GlobalExceptionHandler.handleException(e);
        }

    }

    @Override
    public Product getByIdAndBatchId(int productId, int batchId) {

        long startTime = System.currentTimeMillis();
        Product product = new Product();
        String query = "SELECT product_id, batch_id, product_name, selling_price, image_url, quantity, expiration_date, manufacturer " +
                "FROM product WHERE product_id = ? AND batch_id = ? AND quantity >0";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            stmt.setInt(2, batchId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    readAllFromResultSet(rs, product);
                }
            }

        } catch (SQLException e) {
            GlobalExceptionHandler.handleException(e);
        }
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;

        System.out.println("Query executed in: " + duration + "= ?");

        return product;
    }


    @Override
    public void delete(int product_id, int batch_id) {
        String query = "DELETE FROM product WHERE product_id = ? AND batch_id = ?";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, product_id);
            stmt.setInt(2, batch_id);
            int rowsDeleted = stmt.executeUpdate();

        } catch (SQLException e) {
            GlobalExceptionHandler.handleException(e);
        }
    }

    private void readInformationFromResultSet(ResultSet rs, Product product) throws SQLException {
        product.setProductId(rs.getInt("product_id"));
        product.setBatchId(rs.getInt("batch_id"));
        product.setProductName(rs.getString("product_name"));
        product.setSellingPrice(rs.getDouble("selling_price"));
        product.setImageUrl(rs.getString("image_url"));
        product.setQuantity(rs.getInt("quantity"));
        product.setExpirationDate(rs.getTimestamp("expiration_date"));
    }

    private void readAllFromResultSet (ResultSet rs, Product product) throws SQLException{
        readInformationFromResultSet(rs, product);
        product.setManufacturer(rs.getString("manufacturer"));
    }






}