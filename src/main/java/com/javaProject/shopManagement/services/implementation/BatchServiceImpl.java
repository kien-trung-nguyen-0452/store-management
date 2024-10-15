package com.javaProject.shopManagement.services.implementation;

import com.javaProject.shopManagement.dao.implementation.BatchDAOImpl;
import com.javaProject.shopManagement.dto.BatchDTO;
import com.javaProject.shopManagement.models.Batch;
import com.javaProject.shopManagement.services.interfaces.BatchService;

import java.util.ArrayList;
import java.util.List;

public class BatchServiceImpl implements BatchService {

    public static BatchServiceImpl getInstance(){
        return new BatchServiceImpl();
    }

    @Override
    public List<BatchDTO> getAllBatches() {
        List<Batch> batches = BatchDAOImpl.getInstance().getAll();
        List<BatchDTO> batchDTOS = new ArrayList<>();
        for (Batch batch : batches) {
            BatchDTO batchDTO = new BatchDTO();
            batchToBatchDTO(batch, batchDTO);
            batchDTOS.add(batchDTO);
        }
        return batchDTOS;
    }

    @Override
    public void addBatch(BatchDTO batchDTO) {
        Batch batch = new Batch();
        batchDTOToBatch(batchDTO, batch);
        BatchDAOImpl.getInstance().add( batch );
    }

    @Override
    public List<BatchDTO> getBatchByID(int id) {
        List<Batch> batches = BatchDAOImpl.getInstance().getById(id);
        List<BatchDTO> batchDTOS = new ArrayList<>();
        for (Batch batch : batches) {
            BatchDTO batchDTO = new BatchDTO();
            batchToBatchDTO(batch, batchDTO);
            batchDTOS.add( batchDTO );
        }
        return batchDTOS;
    }

    @Override
    public void deleteBatch(int batchId, int productId) {
        BatchDAOImpl.getInstance().delete(batchId, productId);
    }

    private void batchToBatchDTO (Batch batch, BatchDTO batchDTO) {
        batchDTO.setBatchId( batch.getBatchId() );
        batchDTO.setCreateDate( batch.getCreateDate() );
        batchDTO.setQuantity( batch.getQuantity() );
        batchDTO.setProductName( batch.getProductName() );
        batchDTO.setPurchasePrice( batch.getPurchasePrice() );
        batchDTO.setSupplierName( batch.getSupplierName() );
        batchDTO.setProductId( batch.getProductId() );
    }

    private void batchDTOToBatch (BatchDTO batchDTO, Batch batch) {
        batch.setBatchId( batchDTO.getBatchId() );
        batch.setCreateDate( batchDTO.getCreateDate() );
        batch.setQuantity( batchDTO.getQuantity() );
        batch.setProductName( batchDTO.getProductName() );
        batch.setPurchasePrice( batchDTO.getPurchasePrice() );
        batch.setSupplierName( batchDTO.getSupplierName() );
        batch.setProductId( batchDTO.getProductId() );
    }
}
