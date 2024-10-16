package com.javaProject.shopManagement.services.implementation;

import com.javaProject.shopManagement.dao.implementation.BatchDAOImpl;
import com.javaProject.shopManagement.dto.BatchDTO;
import com.javaProject.shopManagement.mapper.BatchMapper;
import com.javaProject.shopManagement.entity.Batch;
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
            BatchDTO batchDTO = BatchMapper.toDto(batch);
            batchDTOS.add(batchDTO);
        }
        return batchDTOS;
    }

    @Override
    public void addBatch(BatchDTO batchDTO) {
        Batch batch = BatchMapper.toEntity(batchDTO);
        BatchDAOImpl.getInstance().add( batch );
    }

    @Override
    public List<BatchDTO> getBatchByID(int id) {
        List<Batch> batches = BatchDAOImpl.getInstance().getById(id);
        List<BatchDTO> batchDTOS = new ArrayList<>();
        for (Batch batch : batches) {
            BatchDTO batchDTO = BatchMapper.toDto(batch);
            batchDTOS.add( batchDTO );
        }
        return batchDTOS;
    }

    @Override
    public void deleteBatch(int batchId, int productId) {
        BatchDAOImpl.getInstance().delete(batchId, productId);
    }
}
