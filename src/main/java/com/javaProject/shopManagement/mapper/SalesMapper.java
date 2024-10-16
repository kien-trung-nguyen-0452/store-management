package com.javaProject.shopManagement.mapper;

import com.javaProject.shopManagement.dto.SalesDTO;
import com.javaProject.shopManagement.models.Sales;

public class SalesMapper {
    public static Sales toEntity(SalesDTO dto){
        Sales salesEntity = new Sales();
        salesEntity.setProductName(dto.getProductName());
        salesEntity.setInvoiceId(dto.getInvoiceId());
        salesEntity.setQuantity(dto.getQuantity());
        salesEntity.setPrice(dto.getPrice());
        salesEntity.setBatchId(dto.getBatchId());
        salesEntity.setProductId(dto.getProductId());
        return salesEntity;
    }
    public static SalesDTO toDTO(Sales entity){
        SalesDTO salesDTO = new SalesDTO();
        salesDTO.setProductName(entity.getProductName());
        salesDTO.setInvoiceId(entity.getInvoiceId());
        salesDTO.setQuantity(entity.getQuantity());
        salesDTO.setPrice(entity.getPrice());
        salesDTO.setBatchId(entity.getBatchId());
        salesDTO.setProductId(entity.getProductId());
        return salesDTO;
    }
}
