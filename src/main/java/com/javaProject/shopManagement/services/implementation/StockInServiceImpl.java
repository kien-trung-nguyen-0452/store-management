package com.javaProject.shopManagement.services.implementation;

import com.javaProject.shopManagement.dto.BatchInfoDTO;
import com.javaProject.shopManagement.dto.StockInRequest;
import com.javaProject.shopManagement.mapper.StockInRequestMapper;
import com.javaProject.shopManagement.services.interfaces.StockInService;

import java.util.HashSet;
import java.util.List;

public class StockInServiceImpl implements StockInService {
    public static StockInServiceImpl getInstance(){
        return new StockInServiceImpl();
    }
    @Override
    public void stockIn(BatchInfoDTO batchInfoDTO, HashSet<StockInRequest> stockInRequestList) {
        BatchServiceImpl batchServiceImpl = new BatchServiceImpl();
        ProductServiceImpl productService = new ProductServiceImpl();
        for (StockInRequest stockInRequest : stockInRequestList) {
            batchServiceImpl.addBatch(StockInRequestMapper.mapToBatchDTO(stockInRequest, batchInfoDTO));
            productService.add(StockInRequestMapper.mapToProductDTO(stockInRequest, batchInfoDTO));
        }
    }
}
