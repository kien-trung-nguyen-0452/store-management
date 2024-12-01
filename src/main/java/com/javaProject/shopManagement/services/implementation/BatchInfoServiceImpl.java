package com.javaProject.shopManagement.services.implementation;

import com.javaProject.shopManagement.dao.implementation.BatchInfoDAOImpl;
import com.javaProject.shopManagement.dto.BatchInfoDTO;
import com.javaProject.shopManagement.mapper.BatchInfoMapper;
import com.javaProject.shopManagement.entity.BatchInfo;
import com.javaProject.shopManagement.services.interfaces.BatchInfoService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BatchInfoServiceImpl implements BatchInfoService {
    public static BatchInfoServiceImpl getInstance(){
        return new BatchInfoServiceImpl();
    }


    @Override
    public List<BatchInfoDTO> getAll() {
      List<BatchInfo> batchInfoList = BatchInfoDAOImpl.getInstance().getAll();
      List<BatchInfoDTO> batchInfoDTOList = new ArrayList<>();
      for (BatchInfo batchInfo : batchInfoList) {
          BatchInfoDTO batchInfoDTO = BatchInfoMapper.toDto(batchInfo);
          batchInfoDTOList.add(batchInfoDTO);
      }
      return batchInfoDTOList;
    }

    @Override
    public void add(BatchInfoDTO batchInfoDTO) {
        BatchInfo batchInfo = BatchInfoMapper.toEntity(batchInfoDTO);
        BatchInfoDAOImpl.getInstance().add(batchInfo);
    }

    @Override
    public BatchInfoDTO getById(int id) {
       return BatchInfoMapper.toDto(BatchInfoDAOImpl.getInstance().getById(id));
    }



    @Override
    public void delete(int batchId) {
        BatchInfoDAOImpl.getInstance().delete(batchId);
    }

    @Override
    public void update(BatchInfoDTO batchInfoDTO) {
        BatchInfo batchInfo = BatchInfoMapper.toEntity(batchInfoDTO);
        BatchInfoDAOImpl.getInstance().update(batchInfo);

    }

    @Override
    public List<BatchInfoDTO> getBatchInfoByDateRange(Timestamp startDate, Timestamp endDate) {
        List<BatchInfoDTO> batchInfoDTOList = new ArrayList<>();
        for (BatchInfo batchInfo : BatchInfoDAOImpl.getInstance().getByDateRange(startDate, endDate)) {
            BatchInfoDTO batchInfoDTO = BatchInfoMapper.toDto(batchInfo);
            batchInfoDTOList.add(batchInfoDTO);
        }
        return batchInfoDTOList;
    }

    @Override
    public List<BatchInfoDTO> getBatchInfoBySupplier(String supplier) {
        List<BatchInfoDTO> batchInfoDTOList = new ArrayList<>();
        for(BatchInfo batchInfo: BatchInfoDAOImpl.getInstance().getByCondition(supplier)){
            BatchInfoDTO batchInfoDTO = BatchInfoMapper.toDto(batchInfo);
            batchInfoDTOList.add(batchInfoDTO);

        }
        return batchInfoDTOList;
    }

    @Override
    public BatchInfoDTO getBatchInfoByBatchName(String batchName) {
        return BatchInfoMapper.toDto(BatchInfoDAOImpl.getInstance().getByBatchName(batchName));
    }
}
