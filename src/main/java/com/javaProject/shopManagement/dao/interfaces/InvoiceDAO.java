package com.javaProject.shopManagement.dao.interfaces;

import com.javaProject.shopManagement.entity.Invoice;

import java.sql.Timestamp;
import java.util.List;

public interface InvoiceDAO {
    List<Invoice> getAll();
    Invoice getById(int id);
    List<Invoice> getByCondition(String condition);
    List<Invoice> getByDateRange(Timestamp startDate, Timestamp endDate);
    int add(Invoice entity);
    void update(Invoice entity);
    void delete(int id);
}
