package com.javaProject.shopManagement.mapper;

import com.javaProject.shopManagement.dto.BatchDTO;
import com.javaProject.shopManagement.entity.Batch;

public class BatchMapper {
    public static BatchDTO toDto(Batch entity){
        BatchDTO batchDTO = new BatchDTO();
        batchDTO.setBatchId( entity.getBatchId() );
        batchDTO.setCreateDate( entity.getCreateDate() );
        batchDTO.setQuantity( entity.getQuantity() );
        batchDTO.setProductName( entity.getProductName() );
        batchDTO.setPurchasePrice( entity.getPurchasePrice() );
        batchDTO.setSupplierName( entity.getSupplierName() );
        batchDTO.setProductId( entity.getProductId() );
        return batchDTO;
    }

    public static Batch toEntity(BatchDTO dto){
        Batch batch = new Batch();
        batch.setBatchId( dto.getBatchId() );
        batch.setCreateDate( dto.getCreateDate() );
        batch.setQuantity( dto.getQuantity() );
        batch.setProductName( dto.getProductName() );
        batch.setPurchasePrice( dto.getPurchasePrice() );
        batch.setSupplierName( dto.getSupplierName() );
        batch.setProductId( dto.getProductId() );
        return batch;
    }
}
