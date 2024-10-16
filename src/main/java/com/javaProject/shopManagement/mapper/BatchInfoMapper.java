package com.javaProject.shopManagement.mapper;

import com.javaProject.shopManagement.dto.BatchInfoDTO;
import com.javaProject.shopManagement.models.BatchInfo;

public class BatchInfoMapper {
    public static BatchInfoDTO toDto(BatchInfo entity){
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
}
