package com.javaProject.shopManagement.dao.interfaces;

import com.javaProject.shopManagement.models.BatchInfor;

import java.util.List;

public interface BacthInforDAO {
    List<BatchInfor> getAll();
    BatchInfor getById(int id);
    List<BatchInfor> getByCondition(String condition);
    void add(BatchInfor entity);
    void delete(int id);
}
