package com.javaProject.shopManagement.services.interfaces;

import com.javaProject.shopManagement.dto.InvoiceDTO;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface InvoiceService{

    void addInvoice(InvoiceDTO invoiceDTO);
    List<InvoiceDTO> getAllInvoices();
    InvoiceDTO getInvoice(int id);
    void updateInvoice(InvoiceDTO invoiceDTO);
    void deleteInvoice(int id);
    List<InvoiceDTO> getInvoicesByDateRange(Timestamp startDate, Timestamp endDate);



}
