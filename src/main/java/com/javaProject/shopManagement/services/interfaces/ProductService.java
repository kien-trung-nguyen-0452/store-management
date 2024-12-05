package com.javaProject.shopManagement.services.interfaces;

import com.javaProject.shopManagement.dto.product.ProductDTO;
import com.javaProject.shopManagement.dto.product.ProductStatusDTO;

import java.util.List;

public interface ProductService {
    String getProductNameById(int productId, int batchId);
    void add(ProductDTO product);
    void update(ProductDTO product);
    void delete(int productId, int batchId);
    List<ProductDTO> searchProduct(String keyword);
    List<ProductDTO> getAllProducts();
    List<ProductDTO> getProductById(int id);
    ProductDTO getProductByIdAndBatch (int id, int batch);



}

