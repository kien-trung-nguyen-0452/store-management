package com.javaProject.shopManagement.services.interfaces;

import com.javaProject.shopManagement.dto.BatchInfoDTO;

import java.util.List;

public interface BatchInfoService {
    List<BatchInfoDTO> getAll();
    void add(BatchInfoDTO batchInfoDTO);
    BatchInfoDTO getById(int id);
    void delete(int id);
}
