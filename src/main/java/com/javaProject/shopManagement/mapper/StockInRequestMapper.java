package com.javaProject.shopManagement.mapper;

import com.javaProject.shopManagement.dto.BatchDTO;
import com.javaProject.shopManagement.dto.BatchInfoDTO;
import com.javaProject.shopManagement.dto.ProductDTO;
import com.javaProject.shopManagement.dto.StockInRequest;
import com.javaProject.shopManagement.entity.BatchInfo;
import com.javaProject.shopManagement.entity.Product;

public class StockInRequestMapper {
    public static BatchDTO mapToBatchDTO(StockInRequest stockInRequest, BatchInfoDTO batchInfoDTO) {
        BatchDTO batchDTO = new BatchDTO();
        batchDTO.setBatchId(batchInfoDTO.getBatchId());
        batchDTO.setProductId(stockInRequest.getProductId());
        batchDTO.setProductName(stockInRequest.getProductName());
        batchDTO.setQuantity(stockInRequest.getQuantity());
        batchDTO.setCreateDate(batchInfoDTO.getCreateDate());
        batchDTO.setPurchasePrice(stockInRequest.getPurchasePrice());
        batchDTO.setSupplierName(batchDTO.getSupplierName());
        return batchDTO;
    }
    public static ProductDTO mapToProductDTO(StockInRequest stockInRequest, BatchInfoDTO batchInfoDTO) {
       ProductDTO productDTO = new ProductDTO();
       productDTO.setProductId(stockInRequest.getProductId());
       productDTO.setProductName(stockInRequest.getProductName());
       productDTO.setQuantity(stockInRequest.getQuantity());
       productDTO.setManufacturer(stockInRequest.getManufacturer());
       productDTO.setExpirationDate(stockInRequest.getExpirationDate());
       productDTO.setBatchId(batchInfoDTO.getBatchId());
       productDTO.setSellingPrice(stockInRequest.getPurchasePrice());
       productDTO.setImageUrl(stockInRequest.getImageUrl());
       return productDTO;
    }
}

