package com.javaProject.shopManagement.mapper;

import com.javaProject.shopManagement.dto.BatchDTO;
import com.javaProject.shopManagement.dto.BatchInfoDTO;
import com.javaProject.shopManagement.dto.ProductDTO;
import com.javaProject.shopManagement.entity.BatchInfo;

public class BatchInfoMapper {
    public static BatchInfoDTO toDto(BatchInfo entity){
        if(entity == null){
            return null;
        }
        BatchInfoDTO dto = new BatchInfoDTO();
        dto.setBatchId(entity.getBatchId());
        dto.setBatchName(entity.getBatchName());
        dto.setCreateDate(entity.getCreateDate());
        dto.setDescription(entity.getDescription());
        dto.setSupplier(entity.getSupplier());
        dto.setTotalPrice(entity.getTotalPrice());
        return dto;
    }
    public static BatchInfo toEntity(BatchInfoDTO dto){
        BatchInfo entity = new BatchInfo();
        entity.setBatchId(dto.getBatchId());
        entity.setBatchName(dto.getBatchName());
        entity.setCreateDate(dto.getCreateDate());
        entity.setDescription(dto.getDescription());
        entity.setSupplier(dto.getSupplier());
        entity.setTotalPrice(dto.getTotalPrice());
        return entity;
    }

    public static void mapToBatchDTO(BatchInfoDTO batchInfoDTO, BatchDTO batchDTO){
        batchInfoDTO.setBatchId(batchInfoDTO.getBatchId());
        batchInfoDTO.setBatchName(batchInfoDTO.getBatchName());
        batchInfoDTO.setCreateDate(batchInfoDTO.getCreateDate());
        batchInfoDTO.setDescription(batchInfoDTO.getDescription());
        batchInfoDTO.setSupplier(batchInfoDTO.getSupplier());
        batchInfoDTO.setTotalPrice(batchInfoDTO.getTotalPrice());
    }

    public static void mapToProductDTO(BatchInfoDTO batchInfoDTO, ProductDTO productDTO){
        productDTO.setBatchId(batchInfoDTO.getBatchId());
    }
}
