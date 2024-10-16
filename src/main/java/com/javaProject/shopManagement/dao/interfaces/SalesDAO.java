package com.javaProject.shopManagement.dao.interfaces;

import com.javaProject.shopManagement.models.Sales;

import java.util.List;

public interface SalesDAO {
    List<Sales> getAll();
    List<Sales> getById(int id);
    List<Sales> getByCondition(String condition);
    void add(Sales entity);
    void delete(int id);

}

