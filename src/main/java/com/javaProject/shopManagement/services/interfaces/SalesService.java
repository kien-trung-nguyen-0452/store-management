package com.javaProject.shopManagement.services.interfaces;

import com.javaProject.shopManagement.dto.SalesDTO;

import java.util.List;

public interface SalesService {
    List<SalesDTO> getSalesDetails(int invoiceId);
    void addSales(SalesDTO sales);
    void updateSales(SalesDTO sales);
}
