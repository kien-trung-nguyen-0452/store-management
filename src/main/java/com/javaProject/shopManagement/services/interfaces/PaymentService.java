package com.javaProject.shopManagement.services.interfaces;

import com.javaProject.shopManagement.dto.InvoiceDTO;
import com.javaProject.shopManagement.dto.SalesDTO;

import java.util.List;

public interface PaymentService {
    void pay(InvoiceDTO invoiceDTO, List<SalesDTO> salesDTOList);
}
