package com.javaProject.shopManagement.services;

import com.javaProject.shopManagement.dto.BatchDTO;
import com.javaProject.shopManagement.models.Batch;

import java.util.List;

public interface BatchService {
    List<BatchDTO> getAllBatches();
    void addBatch(BatchDTO batchDTO);
    List<BatchDTO> getBatchByID(int id);
    void deleteBatch(int id);
}
