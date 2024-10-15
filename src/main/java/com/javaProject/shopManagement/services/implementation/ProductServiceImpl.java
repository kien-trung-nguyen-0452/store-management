package com.javaProject.shopManagement.services.implementation;

import com.javaProject.shopManagement.dao.implementation.ProductDAOImpl;
import com.javaProject.shopManagement.dao.interfaces.ProductDAO;
import com.javaProject.shopManagement.dto.ProductDTO;
import com.javaProject.shopManagement.models.Product;
import com.javaProject.shopManagement.services.interfaces.ProductService;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    public static ProductServiceImpl getInstance(){
        return new ProductServiceImpl();
    }
    @Override
    public void add(ProductDTO productDTO) {
        Product product = new Product();
        productDTOToProduct(productDTO, product);
        ProductDAOImpl.getInstance().add(product);
    }

    @Override
    public void update(ProductDTO productDTO) {
        Product product = new Product();
        productDTOToProduct(productDTO, product);
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
        List<Product> products = ProductDAOImpl.getInstance().getById(id);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            productToProductDTO(product, productDTO);
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    @Override
    public ProductDTO getProductByIdAndBatch(int productId, int batchId) {
        ProductDTO productDTO = new ProductDTO();
        Product product = ProductDAOImpl.getInstance().getByIdAndBatchId(productId, batchId);
        productToProductDTO(product, productDTO);
        return productDTO;
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

    private void productDTOToProduct (ProductDTO productDTO, Product product) {
        product.setProductName(productDTO.getProductName());
        product.setProductId(productDTO.getProductId());
        product.setBatchId(productDTO.getBatchId());
        product.setExpirationDate(productDTO.getExpirationDate());
        product.setQuantity(productDTO.getQuantity());
        product.setImageUrl(productDTO.getImageUrl());
        product.setSellingPrice(productDTO.getSellingPrice());
        product.setManufacturer(productDTO.getManufacturer());

    }
}
