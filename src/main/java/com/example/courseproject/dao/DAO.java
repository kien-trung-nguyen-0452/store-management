package com.example.courseproject.dao;
import java.util.List;

public interface DAO<T> {
    List<T> getAll();


    List<T> getById(int id);

    List<T> getByCondition(String condition);

    void add(T entity);

    void update(T entity);

    void delete(int id);


}