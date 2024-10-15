package com.javaProject.shopManagement.dao.interfaces;

import com.javaProject.shopManagement.models.Invoice;

import java.util.List;

public interface InvoiceDAO {
    List<Invoice> getAll();
    Invoice getById(int id);
    List<Invoice> getByCondition(String condition);
    void add(Invoice entity);
    void update(Invoice entity);
    void delete(int id);
}
