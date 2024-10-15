package com.javaProject.shopManagement.services.interfaces;

import com.javaProject.shopManagement.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    void add(ProductDTO product);
    void update(ProductDTO product);
    void delete(ProductDTO product);
    List<ProductDTO> searchProduct(String keyword);
    List<ProductDTO> getAllProducts();
    List<ProductDTO> getProductById(int id);
    ProductDTO getProductByIdAndBatch (int id, int batch);
}

