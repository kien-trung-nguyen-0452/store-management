package com.javaProject.shopManagement.mapper;

import com.javaProject.shopManagement.dto.product.ProductDTO;
import com.javaProject.shopManagement.entity.Product;

public class ProductMapper{

    public static ProductDTO toDto(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName(product.getProductName());
        productDTO.setProductId(product.getProductId());
        productDTO.setBatchId(product.getBatchId());
        productDTO.setExpirationDate(product.getExpirationDate());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setImageUrl(product.getImageUrl());
        productDTO.setSellingPrice(product.getSellingPrice());
        productDTO.setManufacturer(product.getManufacturer());
        return productDTO;
    }


    public static Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setProductId(productDTO.getProductId());
        product.setBatchId(productDTO.getBatchId());
        product.setExpirationDate(productDTO.getExpirationDate());
        product.setQuantity(productDTO.getQuantity());
        product.setImageUrl(productDTO.getImageUrl());
        product.setSellingPrice(productDTO.getSellingPrice());
        product.setManufacturer(productDTO.getManufacturer());
        return product;
    }
}
