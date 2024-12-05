package com.javaProject.shopManagement.dao.interfaces;

import com.javaProject.shopManagement.dto.product.ProductDTO;
import com.javaProject.shopManagement.dto.product.ProductStatusDTO;
import com.javaProject.shopManagement.entity.Product;

import java.util.List;

public interface ProductDAO {
    String getProductNameById(int productId, int batchId);
    List<ProductDTO> getAll();
    List<Product> getById(int id);
    List<Product> getByCondition(String condition);
    Product getByIdAndBatchId(int id, int batchId);
    void add(Product entity);
    void update(Product entity);
    void delete(int id, int batchId);
    List<ProductStatusDTO> getExpiringProducts(int dateRange);
    List<ProductStatusDTO> getExpiredProducts();

}
