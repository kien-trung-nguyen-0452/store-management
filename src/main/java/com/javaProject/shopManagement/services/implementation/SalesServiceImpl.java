package com.javaProject.shopManagement.services.implementation;

import com.javaProject.shopManagement.dao.implementation.SalesDAOImpl;
import com.javaProject.shopManagement.dto.SalesDTO;
import com.javaProject.shopManagement.mapper.SalesMapper;
import com.javaProject.shopManagement.entity.Sales;
import com.javaProject.shopManagement.services.interfaces.SalesService;

import java.util.ArrayList;
import java.util.List;

public class SalesServiceImpl implements SalesService {

    public static SalesServiceImpl getInstance(){
        return new SalesServiceImpl();
    }

    @Override
    public List<SalesDTO> getSalesDetails(int invoiceId) {
        List<Sales> salesList = SalesDAOImpl.getInstance().getById(invoiceId);
        List<SalesDTO> salesDTOList = new ArrayList<>();
        for (Sales sales : salesList) {
            sales.setProductName(ProductServiceImpl.getInstance().getProductNameById(sales.getProductId(), sales.getBatchId()));
            SalesDTO salesDTO = SalesMapper.toDTO(sales);
            salesDTOList.add(salesDTO);
        }
        return salesDTOList;
    }

    @Override
    public void addSales(SalesDTO salesDTO) {
        Sales sales = SalesMapper.toEntity(salesDTO);
        SalesDAOImpl.getInstance().add(sales);

    }

    @Override
    public void updateSales(SalesDTO salesDTO) {
    }

}
