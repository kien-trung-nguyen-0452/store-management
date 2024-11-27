package com.javaProject.shopManagement.services.implementation;

import com.javaProject.shopManagement.dto.InvoiceDTO;
import com.javaProject.shopManagement.dto.ProductDTO;
import com.javaProject.shopManagement.dto.SalesDTO;
import com.javaProject.shopManagement.services.interfaces.PaymentService;

import java.util.List;

public class PaymentServiceImpl implements PaymentService {
    public static PaymentServiceImpl getInstance() {
        return new PaymentServiceImpl();
    }

    @Override
    public void pay(InvoiceDTO invoiceDTO, List<SalesDTO> salesDTOList) {
        int invoiceId = InvoiceServiceImpl.getInstance().addInvoiceAndGetInvoiceCode(invoiceDTO);
        for (SalesDTO salesDTO : salesDTOList) {
            salesDTO.setInvoiceId(invoiceId);
            ProductDTO productDTO = ProductServiceImpl.getInstance().getProductByIdAndBatch(salesDTO.getProductId(), salesDTO.getBatchId());
            productDTO.setQuantity(productDTO.getQuantity() - salesDTO.getQuantity());
            ProductServiceImpl.getInstance().update(productDTO);
            SalesServiceImpl.getInstance().addSales(salesDTO);
        }
    }
}
