package com.javaProject.shopManagement.dao.implementation;
import com.javaProject.shopManagement.config.DbUtils;
import com.javaProject.shopManagement.dao.interfaces.ProductDAO;
import com.javaProject.shopManagement.dto.product.ExpirationStatus;
import com.javaProject.shopManagement.dto.product.ProductDTO;
import com.javaProject.shopManagement.dto.product.ProductStatusDTO;
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
    public String getProductNameById(int productId, int batchId) {
        String query = "SELECT product_name FROM product WHERE product_id = ? AND batch_id = ?";
        String productName = "";
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            stmt.setInt(2, batchId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    productName = rs.getString("product_name");
                }
            }
        } catch (SQLException e) {
            GlobalExceptionHandler.handleException(e);
        }
        return productName;
    }

    @Override
    public List<ProductDTO> getAll() {
        long startTime = System.currentTimeMillis();
        List<ProductDTO> products = new ArrayList<>();
        String query = """
            SELECT p.product_id, p.batch_id, p.product_name, p.selling_price, p.image_url, p.quantity, p.expiration_date, b.purchase_price, p.manufacturer 
            FROM product p 
            JOIN batch b ON p.batch_id = b.batch_id AND p.product_id = b.product_id
            WHERE p.quantity > 0
        """;

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ProductDTO product = new ProductDTO();
                product.setProductId(rs.getInt("product_id"));
                product.setBatchId(rs.getInt("batch_id"));
                product.setProductName(rs.getString("product_name"));
                product.setSellingPrice(rs.getDouble("selling_price"));
                product.setImageUrl(rs.getString("image_url"));
                product.setQuantity(rs.getInt("quantity"));
                product.setExpirationDate(rs.getTimestamp("expiration_date"));
                product.setManufacturer(rs.getString("manufacturer"));
                product.setPurchasePrice(rs.getDouble("purchase_price"));
                products.add(product);
            }
        } catch (SQLException e) {
            GlobalExceptionHandler.handleException(e);
        }

        long endTime = System.currentTimeMillis();
        System.out.printf("Query executed in: %d ms%n", (endTime - startTime));
        return products;
    }

    @Override
    public List<Product> getById(int id) {
        long startTime = System.currentTimeMillis();
        List<Product> products = new ArrayList<>();
        String query = "SELECT product_id, batch_id, product_name, selling_price, image_url, quantity, expiration_date, manufacturer FROM product WHERE product_id = ? AND quantity > 0";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("product_id"));
                    product.setBatchId(rs.getInt("batch_id"));
                    product.setProductName(rs.getString("product_name"));
                    product.setSellingPrice(rs.getDouble("selling_price"));
                    product.setImageUrl(rs.getString("image_url"));
                    product.setQuantity(rs.getInt("quantity"));
                    product.setExpirationDate(rs.getTimestamp("expiration_date"));
                    product.setManufacturer(rs.getString("manufacturer"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            GlobalExceptionHandler.handleException(e);
        }

        long endTime = System.currentTimeMillis();
        System.out.printf("Query executed in: %d ms%n", (endTime - startTime));
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
            stmt.executeUpdate();

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
            stmt.executeUpdate();

        } catch (SQLException e) {
            GlobalExceptionHandler.handleException(e);
        }
    }

    @Override
    public List<ProductStatusDTO> getExpiringProducts(int dateRange) {
        List<ProductStatusDTO> list = new ArrayList<>();
        String query = """
                SELECT product_id, product_name, batch_id, expiration_date, image_url
                FROM product
                WHERE expiration_date BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL ? DAY)
                ORDER BY expiration_date DESC
                """;
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, dateRange);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProductStatusDTO productStatusDTO = new ProductStatusDTO();
                productStatusDTO.setExpirationStatus(ExpirationStatus.EXPIRING_SOON);
                productStatusDTO.setProductName(rs.getString("product_name"));
                productStatusDTO.setBatchId(rs.getInt("batch_id"));
                productStatusDTO.setProductId(rs.getInt("product_id"));
                productStatusDTO.setImageUrl(rs.getString("image_url"));
                productStatusDTO.setExpirationDate(rs.getTimestamp("expiration_date"));
                list.add(productStatusDTO);
            }

        }catch (SQLException e) {
            GlobalExceptionHandler.handleException(e);
        }
        return list;
    }

    @Override
    public List<ProductStatusDTO> getExpiredProducts() {
        String query = """
                SELECT product_id, product_name, batch_id, expiration_date, image_url
                FROM product
                WHERE expiration_date < NOW() AND quantity > 0
                ORDER BY expiration_date DESC
                """;
        List<ProductStatusDTO> list = new ArrayList<>();
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProductStatusDTO productStatusDTO = new ProductStatusDTO();
                productStatusDTO.setExpirationStatus(ExpirationStatus.EXPIRED);
                productStatusDTO.setProductName(rs.getString("product_name"));
                productStatusDTO.setBatchId(rs.getInt("batch_id"));
                productStatusDTO.setProductId(rs.getInt("product_id"));
                productStatusDTO.setImageUrl(rs.getString("image_url"));
                productStatusDTO.setExpirationDate(rs.getTimestamp("expiration_date"));
                list.add(productStatusDTO);
            }

        }catch (SQLException e) {
            GlobalExceptionHandler.handleException(e);
        }
        return list;
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