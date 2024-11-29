package com.javaProject.shopManagement.services.interfaces;

import com.javaProject.shopManagement.dto.BatchInfoDTO;
import com.javaProject.shopManagement.dto.StockInRequest;

import java.util.HashSet;
import java.util.List;

public interface StockInService {
    void stockIn(BatchInfoDTO batchInfoDTO, HashSet<StockInRequest> stockInRequestList);
}
