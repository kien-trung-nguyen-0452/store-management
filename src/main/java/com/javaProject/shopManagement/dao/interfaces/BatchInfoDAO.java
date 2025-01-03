package com.javaProject.shopManagement.dao.interfaces;

import com.javaProject.shopManagement.entity.BatchInfo;

import java.sql.Timestamp;
import java.util.List;

public interface BatchInfoDAO {
    List<BatchInfo> getAll();
    BatchInfo getById(int id);
    List<BatchInfo> getByCondition(String condition);
    void add(BatchInfo entity);
    void delete(int id);
    void update(BatchInfo entity);
    List<BatchInfo> getByDateRange(Timestamp start, Timestamp end);
    BatchInfo getByBatchName(String batchName);


}
