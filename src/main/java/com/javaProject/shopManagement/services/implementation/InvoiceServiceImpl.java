package com.javaProject.shopManagement.services.implementation;

import com.javaProject.shopManagement.dao.implementation.InvoiceDAOImpl;
import com.javaProject.shopManagement.dto.InvoiceDTO;
import com.javaProject.shopManagement.models.Invoice;
import com.javaProject.shopManagement.services.interfaces.InvoiceService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class InvoiceServiceImpl implements InvoiceService {
    @Override
    public void addInvoice(InvoiceDTO invoiceDTO) {
        Invoice invoice = new Invoice();
        invoiceDTOToInvoice(invoiceDTO, invoice);
        InvoiceDAOImpl.getInstance().add(invoice);
    }

    @Override
    public List<InvoiceDTO> getAllInvoices() {
        List<Invoice> invoices = InvoiceDAOImpl.getInstance().getAll();
        List<InvoiceDTO> invoiceDTOList = new ArrayList<>();
        for (Invoice invoice : invoices) {
            InvoiceDTO invoiceDTO = new InvoiceDTO();
            invoiceToInvoiceDTO(invoice, invoiceDTO);
            invoiceDTOList.add(invoiceDTO);
        }
        return invoiceDTOList;
    }

    @Override
    public InvoiceDTO getInvoice(int id) {
        Invoice invoice = InvoiceDAOImpl.getInstance().getById(id);
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceToInvoiceDTO(invoice, invoiceDTO);
        return invoiceDTO;
    }

    @Override
    public void updateInvoice(InvoiceDTO invoiceDTO) {
        Invoice invoice = new Invoice();
        invoiceDTOToInvoice(invoiceDTO, invoice);
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
            InvoiceDTO invoiceDTO = new InvoiceDTO();
            invoiceToInvoiceDTO(invoice, invoiceDTO);
            invoicesDTO.add(invoiceDTO);
        }
        return invoicesDTO;
    }

    private void invoiceDTOToInvoice(InvoiceDTO invoiceDTO, Invoice invoice) {
        invoice.setInvoiceId(invoiceDTO.getInvoiceId());
        invoice.setDate(invoiceDTO.getDate());
        invoice.setTotalAmount(invoiceDTO.getTotalAmount());
    }

    private void invoiceToInvoiceDTO(Invoice invoice, InvoiceDTO invoiceDTO) {
        invoiceDTO.setInvoiceId(invoice.getInvoiceId());
        invoiceDTO.setDate(invoice.getDate());
        invoiceDTO.setTotalAmount(invoice.getTotalAmount());
    }
}
