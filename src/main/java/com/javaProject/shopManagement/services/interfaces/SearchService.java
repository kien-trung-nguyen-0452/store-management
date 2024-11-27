package com.javaProject.shopManagement.services.interfaces;

import com.javaProject.shopManagement.dto.ProductDTO;
import com.javaProject.shopManagement.entity.Product;

import java.util.List;

public interface SearchService {
    List<ProductDTO> searchProduct(String keyword);
}
