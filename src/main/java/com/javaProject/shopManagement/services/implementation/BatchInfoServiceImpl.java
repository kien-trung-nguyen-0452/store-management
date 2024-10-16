package com.javaProject.shopManagement.services.implementation;

import com.javaProject.shopManagement.dao.implementation.BatchInfoDAOImpl;
import com.javaProject.shopManagement.dto.BatchInfoDTO;
import com.javaProject.shopManagement.mapper.BatchInfoMapper;
import com.javaProject.shopManagement.entity.BatchInfo;
import com.javaProject.shopManagement.services.interfaces.BatchInfoService;

import java.util.ArrayList;
import java.util.List;

public class BatchInfoServiceImpl implements BatchInfoService {
    public static BatchServiceImpl getInstance(){
        return new BatchServiceImpl();
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
}
