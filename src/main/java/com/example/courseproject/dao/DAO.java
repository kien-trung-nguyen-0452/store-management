package com.example.courseproject.dao;
import java.util.List;

public interface DAO<T>{

    List<T> getAll();           // Lấy tất cả các entity
    T getById(int id);          // Lấy entity theo ID
    void add(T entity);         // Thêm entity mới
    void update(T entity);      // Cập nhật entity
    void delete(int id);        // Xóa entity theo ID
}
