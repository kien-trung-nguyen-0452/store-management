package com.javaProject.shopManagement.services.implementation;

import com.javaProject.shopManagement.dao.implementation.ProductDAOImpl;
import com.javaProject.shopManagement.dto.product.ProductDTO;
import com.javaProject.shopManagement.dto.product.ProductStatusDTO;
import com.javaProject.shopManagement.mapper.ProductMapper;
import com.javaProject.shopManagement.entity.Product;
import com.javaProject.shopManagement.services.interfaces.ProductService;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    public static ProductServiceImpl getInstance(){
        return new ProductServiceImpl();
    }

    @Override
    public String getProductNameById(int productId, int batchId) {
        return ProductDAOImpl.getInstance().getProductNameById(productId, batchId);
    }

    @Override
    public void add(ProductDTO productDTO) {
        Product product = ProductMapper.toEntity(productDTO);
        ProductDAOImpl.getInstance().add(product);
    }

    @Override
    public void update(ProductDTO productDTO) {
        Product product = ProductMapper.toEntity(productDTO);
        ProductDAOImpl.getInstance().update(product);
    }

    @Override
    public void delete(int productId, int batchId) {
        ProductDAOImpl.getInstance().delete(productId, batchId);
    }

    @Override
    public List<ProductDTO> searchProduct(String keyword) {
       List<Product> products = ProductDAOImpl.getInstance().getByCondition(keyword);
       List<ProductDTO> productDTOS = new ArrayList<>();
       for (Product product : products) {
           ProductDTO productDTO = ProductMapper.toDto(product);
           productDTOS.add(productDTO);
       }
       return productDTOS;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
       return ProductDAOImpl.getInstance().getAll();
    }

    @Override
    public List<ProductDTO> getProductById(int id) {
        List<Product> products = ProductDAOImpl.getInstance().getById(id);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = ProductMapper.toDto(product);
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    @Override
    public ProductDTO getProductByIdAndBatch(int productId, int batchId) {
        Product product = ProductDAOImpl.getInstance().getByIdAndBatchId(productId, batchId);
        return ProductMapper.toDto(product);
    }



}
