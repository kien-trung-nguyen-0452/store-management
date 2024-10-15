package com.javaProject.shopManagement.dao;

import com.javaProject.shopManagement.config.DbUtils;
import com.javaProject.shopManagement.exception.GlobalExeptionHandler;
import com.javaProject.shopManagement.models.Invoice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAOImpl implements DAO<Invoice> {

    public static InvoiceDAOImpl getInstance() {
        return new InvoiceDAOImpl();
    }

    @Override
    public List<Invoice> getAll() {
        List<Invoice> invoices = new ArrayList<>();
        String query = "SELECT invoice_code, total_revenue, invoice_date FROM invoice";
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Invoice invoice = new Invoice(
                            rs.getInt("invoice_code"),
                            rs.getTimestamp("invoice_date"),
                            rs.getDouble("total_revenue")
                    );
                    invoices.add(invoice);
                }

            }
        }catch (SQLException e) {
            GlobalExeptionHandler.handleException(e);
        }

        return invoices;
    }

    @Override
    public List<Invoice> getById(int invoice_code) {
        String query = "SELECT invoice_code, total_revenue, invoice_date FROM invoice WHERE invoice_code = ?";
        List<Invoice> invoices = new ArrayList<>();

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, invoice_code);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Invoice invoice = new Invoice(
                            rs.getInt("invoice_code"),
                            rs.getTimestamp("invoice_date"),
                            rs.getDouble("total_revenue")
                    );
                }
            }

        } catch (SQLException e) {
           GlobalExeptionHandler.handleException(e);
        }

        return invoices;
    }

    @Override
    public List<Invoice> getByCondition(String condition) {
        long startTime = System.currentTimeMillis();
        List<Invoice> invoices = new ArrayList<>();

        if (condition == null || condition.isEmpty()) {
            return null;
        }

        String query = "SELECT invoice_code, total_revenue, invoice_date FROM invoice WHERE " + condition;

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Invoice invoice = new Invoice(
                            rs.getInt("invoice_code"),
                            rs.getTimestamp("invoice_date"),
                            rs.getDouble("total_revenue")
                    );
                    invoices.add(invoice);
                }
            }
        } catch (SQLException e) {
           GlobalExeptionHandler.handleException(e);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Query executed in: " + duration + " ms");
        return invoices;
    }

    @Override
    public void add(Invoice invoice) {
        String query = "INSERT INTO invoice (invoice_code, total_revenue, invoice_date) VALUES (?, ?, ?)";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, invoice.getInvoiceId());
            stmt.setDouble(2, invoice.getTotalAmount());
            stmt.setTimestamp(3, invoice.getDate());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        invoice.setInvoiceId(generatedKeys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            GlobalExeptionHandler.handleException(e);
        }
    }

    @Override
    public void update(Invoice invoice) {
        String query = "UPDATE invoice SET total_revenue = ?, invoice_date = ? WHERE invoice_code = ?";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, invoice.getTotalAmount());
            stmt.setTimestamp(2, invoice.getDate());
            stmt.setInt(3, invoice.getInvoiceId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            GlobalExeptionHandler.handleException(e);
        }
    }

    @Override
    public void delete(int invoice_code) {
        String query = "DELETE FROM invoice WHERE invoice_code = ?";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, invoice_code);
            stmt.executeUpdate();

        } catch (SQLException e) {
            GlobalExeptionHandler.handleException(e);
        }
    }
}