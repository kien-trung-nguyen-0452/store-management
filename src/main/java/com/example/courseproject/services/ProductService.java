package com.example.courseproject.services;

import com.example.courseproject.dao.ProductDAO;
import com.example.courseproject.models.Product;

import java.util.List;

public class ProductService {

        private final ProductDAO productDAO;

        public ProductService(ProductDAO productDAO) {
            this.productDAO = productDAO;
        }

        public List<Product> getAllProducts() {
            return productDAO.getAll();
        }

        public Product getProductById(int id) {
            return productDAO.getById(id);
        }

        public void addProduct(Product product) {
            productDAO.add(product);
        }

        public void updateProduct(Product product) {
            productDAO.update(product);
        }

        public void deleteProduct(int id) {
            productDAO.delete(id);
        }
    }

