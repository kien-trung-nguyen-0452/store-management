package com.javaProject.shopManagement.mapper;

import com.javaProject.shopManagement.dto.InvoiceDTO;
import com.javaProject.shopManagement.entity.Invoice;

public class InvoiceMapper {

    public static Invoice toEntity(InvoiceDTO invoiceDTO) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(invoiceDTO.getInvoiceId());
        invoice.setDate(invoiceDTO.getDate());
        invoice.setTotalAmount(invoiceDTO.getTotalAmount());
        return invoice;
    }

    public static InvoiceDTO toDto(Invoice invoice) {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceId(invoice.getInvoiceId());
        invoiceDTO.setDate(invoice.getDate());
        invoiceDTO.setTotalAmount(invoice.getTotalAmount());
        return invoiceDTO;
    }
}
