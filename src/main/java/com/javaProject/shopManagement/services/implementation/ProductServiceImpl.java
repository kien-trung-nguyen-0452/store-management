package com.javaProject.shopManagement.services.implementation;

import com.javaProject.shopManagement.dao.implementation.ProductDAOImpl;
import com.javaProject.shopManagement.dao.interfaces.ProductDAO;
import com.javaProject.shopManagement.dto.ProductDTO;
import com.javaProject.shopManagement.models.Product;
import com.javaProject.shopManagement.services.interfaces.ProductService;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    @Override
    public void add(ProductDTO productDTO) {
        Product product = new Product();
        productToProductDTO(product, productDTO);
        ProductDAOImpl.getInstance().add(product);
    }

    @Override
    public void update(ProductDTO productDTO) {

    }

    @Override
    public void delete(ProductDTO productDTO) {

    }

    @Override
    public List<ProductDTO> searchProduct(String keyword) {
       List<Product> products = ProductDAOImpl.getInstance().getByCondition(keyword);
       List<ProductDTO> productDTOS = new ArrayList<>();
       for (Product product : products) {
           ProductDTO productDTO = new ProductDTO();
           productToProductDTO(product, productDTO);
           productDTOS.add(productDTO);
       }
       return productDTOS;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = ProductDAOImpl.getInstance().getAll();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            productToProductDTO(product, productDTO);
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    @Override
    public List<ProductDTO> getProductById(int id) {
        return List.of();
    }

    @Override
    public ProductDTO getProductByIdAndBatch(int id, int batch) {
        return null;
    }

    private void productToProductDTO(Product product, ProductDTO productDTO) {
        productDTO.setProductName(product.getProductName());
        productDTO.setProductId(product.getProductId());
        productDTO.setBatchId(product.getBatchId());
        productDTO.setExpirationDate(product.getExpirationDate());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setImageUrl(product.getImageUrl());
        productDTO.setSellingPrice(product.getSellingPrice());
        productDTO.setManufacturer(product.getManufacturer());
    }
}
