package com.javaProject.shopManagement.services.implementation;

import com.javaProject.shopManagement.dao.implementation.InvoiceDAOImpl;
import com.javaProject.shopManagement.dto.InvoiceDTO;
import com.javaProject.shopManagement.mapper.InvoiceMapper;
import com.javaProject.shopManagement.models.Invoice;
import com.javaProject.shopManagement.services.interfaces.InvoiceService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class InvoiceServiceImpl implements InvoiceService {
    @Override
    public void addInvoice(InvoiceDTO invoiceDTO) {
        Invoice invoice = InvoiceMapper.toEntity(invoiceDTO);
        InvoiceDAOImpl.getInstance().add(invoice);
    }

    @Override
    public List<InvoiceDTO> getAllInvoices() {
        List<Invoice> invoices = InvoiceDAOImpl.getInstance().getAll();
        List<InvoiceDTO> invoiceDTOList = new ArrayList<>();
        for (Invoice invoice : invoices) {
            InvoiceDTO invoiceDTO = InvoiceMapper.toDto(invoice);
            invoiceDTOList.add(invoiceDTO);
        }
        return invoiceDTOList;
    }

    @Override
    public InvoiceDTO getInvoice(int id) {
        Invoice invoice = InvoiceDAOImpl.getInstance().getById(id);
        return InvoiceMapper.toDto(invoice);
    }

    @Override
    public void updateInvoice(InvoiceDTO invoiceDTO) {
        Invoice invoice = InvoiceMapper.toEntity(invoiceDTO);
        InvoiceDAOImpl.getInstance().update(invoice);
    }

    @Override
    public void deleteInvoice(int id) {
        InvoiceDAOImpl.getInstance().delete(id);
    }

    @Override
    public List<InvoiceDTO> getInvoicesByDateRange(Timestamp startDate, Timestamp endDate) {
        List<Invoice> invoices = InvoiceDAOImpl.getInstance().getByDateRange(startDate, endDate);
        List<InvoiceDTO> invoicesDTO = new ArrayList<>();
        for (Invoice invoice : invoices) {
            InvoiceDTO invoiceDTO = InvoiceMapper.toDto(invoice);
            invoicesDTO.add(invoiceDTO);
        }
        return invoicesDTO;
    }
}
