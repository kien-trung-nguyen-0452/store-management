package com.javaProject.shopManagement.services;

import com.javaProject.shopManagement.dto.ProductDTO;
import com.javaProject.shopManagement.models.Product;

import java.util.List;

public interface ProductService {
    public void add(ProductDTO product);
    public void update(ProductDTO product);
    public void delete(ProductDTO product);
    public List<ProductDTO> searchProduct(String keyword);
    public List<ProductDTO> getAllProducts();
    public List<ProductDTO> getProductById(int id);
    public ProductDTO getProductByIdAndBatch (int id, int batch);
}

