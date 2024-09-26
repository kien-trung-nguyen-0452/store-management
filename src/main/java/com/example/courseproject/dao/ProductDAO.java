package com.example.courseproject.dao;

import com.example.courseproject.models.Product;

import java.util.List;

public class ProductDAO implements DAO<Product> {
    @Override
    public List<Product> getAll() {
        return List.of();
    }

    @Override
    public Product getById(int id) {
        return null;
    }

    @Override
    public void add(Product entity) {

    }

    @Override
    public void update(Product entity) {

    }

    @Override
    public void delete(int id) {

    }
}
