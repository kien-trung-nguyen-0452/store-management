package com.example.courseproject.dao;

import com.example.courseproject.models.Batch;

import java.util.List;

public class BatchDAO implements DAO<Batch> {
    @Override
    public List<Batch> getAll() {
        return List.of();
    }

    @Override
    public Batch getById(int id) {
        return null;
    }

    @Override
    public void add(Batch entity) {

    }

    @Override
    public void update(Batch entity) {

    }

    @Override
    public void delete(int id) {

    }
}
