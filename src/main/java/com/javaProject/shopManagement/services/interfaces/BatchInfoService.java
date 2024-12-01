package com.javaProject.shopManagement.services.interfaces;

import com.javaProject.shopManagement.dto.BatchInfoDTO;

import java.sql.Timestamp;
import java.util.List;

public interface BatchInfoService {
    List<BatchInfoDTO> getAll();
    void add(BatchInfoDTO batchInfoDTO);
    BatchInfoDTO getById(int id);
    void delete(int id);
    void update(BatchInfoDTO batchInfoDTO);
    List<BatchInfoDTO> getBatchInfoByDateRange(Timestamp startDate, Timestamp endDate);
    List<BatchInfoDTO> getBatchInfoBySupplier(String supplier);
    BatchInfoDTO getBatchInfoByBatchName(String batchName);
}
