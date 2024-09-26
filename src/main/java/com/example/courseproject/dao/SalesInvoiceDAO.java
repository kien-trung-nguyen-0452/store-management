package com.example.courseproject.dao;

import com.example.courseproject.models.SalesInvoice;

import java.util.List;

public class SalesInvoiceDAO implements DAO<SalesInvoice> {
    @Override
    public List<SalesInvoice> getAll() {
        return List.of();
    }

    @Override
    public SalesInvoice getById(int id) {
        return null;
    }

    @Override
    public void add(SalesInvoice entity) {

    }

    @Override
    public void update(SalesInvoice entity) {

    }

    @Override
    public void delete(int id) {

    }
}
