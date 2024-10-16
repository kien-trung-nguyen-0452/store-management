package com.javaProject.shopManagement.dao.interfaces;

import com.javaProject.shopManagement.entity.Product;

import java.util.List;

public interface ProductDAO {
    List<Product> getAll();
    List<Product> getById(int id);
    List<Product> getByCondition(String condition);
    Product getByIdAndBatchId(int id, int batchId);
    void add(Product entity);
    void update(Product entity);
    void delete(int id, int batchId);

}
