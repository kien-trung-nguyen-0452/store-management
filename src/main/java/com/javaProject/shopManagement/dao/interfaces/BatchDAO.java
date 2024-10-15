package com.javaProject.shopManagement.dao.interfaces;

import com.javaProject.shopManagement.models.Batch;
import java.util.List;

public interface BatchDAO {
    List<Batch> getAll();
    List<Batch> getById(int id);
    List<Batch> getByCondition(String condition);
    void add(Batch entity);
    void update(Batch entity);
    void delete(int batchId, int productId);
}
