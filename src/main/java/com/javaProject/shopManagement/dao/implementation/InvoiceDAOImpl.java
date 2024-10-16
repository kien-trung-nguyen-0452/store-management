package com.javaProject.shopManagement.dao.implementation;

import com.javaProject.shopManagement.config.DbUtils;
import com.javaProject.shopManagement.dao.interfaces.InvoiceDAO;
import com.javaProject.shopManagement.exception.GlobalExceptionHandler;
import com.javaProject.shopManagement.entity.Invoice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class InvoiceDAOImpl implements InvoiceDAO {

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
                   Invoice invoice = new Invoice();
                   readAllFromResultSet(rs, invoice);
                    invoices.add(invoice);
                }

            }
        }catch (SQLException e) {
            GlobalExceptionHandler.handleException(e);
        }

        return invoices;
    }

    @Override
    public Invoice getById(int invoice_code) {
        String query = "SELECT invoice_code, total_revenue, invoice_date FROM invoice WHERE invoice_code = ?";

        Invoice invoice = new Invoice();
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, invoice_code);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    readAllFromResultSet(rs, invoice);
                }
            }

        } catch (SQLException e) {
           GlobalExceptionHandler.handleException(e);
        }

        return invoice;
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
                    Invoice invoice = new Invoice();
                    readAllFromResultSet(rs, invoice);
                    invoices.add(invoice);
                }
            }
        } catch (SQLException e) {
           GlobalExceptionHandler.handleException(e);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Query executed in: " + duration + " ms");
        return invoices;
    }

    @Override
    public List<Invoice> getByDateRange(Timestamp startDate, Timestamp endDate) {
        List<Invoice> invoices = new ArrayList<>();
        String query = "SELECT invoice_code, total_revenue, invoice_date FROM invoice WHERE invoice_date BETWEEN ? AND ? ORDER BY invoice_date ASC";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setTimestamp(1, startDate);
            stmt.setTimestamp(2, endDate);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Invoice invoice = new Invoice();
                    readAllFromResultSet(rs, invoice);
                    invoices.add(invoice);
                }
            }
        } catch (SQLException e) {
            GlobalExceptionHandler.handleException(e);
        }

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

        } catch (SQLException e) {
            GlobalExceptionHandler.handleException(e);
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
            GlobalExceptionHandler.handleException(e);
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
            GlobalExceptionHandler.handleException(e);
        }
    }

    private void readAllFromResultSet(ResultSet rs, Invoice invoice) throws SQLException {
        invoice.setInvoiceId(rs.getInt("invoice_code"));
        invoice.setDate(rs.getTimestamp("invoice_date"));
        invoice.setTotalAmount(rs.getDouble("total_revenue"));
    }
}