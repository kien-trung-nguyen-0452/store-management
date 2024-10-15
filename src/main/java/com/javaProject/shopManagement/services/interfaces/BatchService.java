package com.javaProject.shopManagement.services.interfaces;

import com.javaProject.shopManagement.dto.BatchDTO;

import java.util.List;

public interface BatchService {
    List<BatchDTO> getAllBatches();
    void addBatch(BatchDTO batchDTO);
    List<BatchDTO> getBatchByID(int id);
    void deleteBatch(int batchId, int productId);
}
