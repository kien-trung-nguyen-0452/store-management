package com.javaProject.shopManagement.services.interfaces;

import com.javaProject.shopManagement.dto.BatchInfoDTO;
import com.javaProject.shopManagement.dto.InvoiceDTO;
import com.javaProject.shopManagement.dto.ProductDTO;
import com.javaProject.shopManagement.entity.Product;

import java.sql.Timestamp;
import java.util.List;

public interface SearchService {
    List<ProductDTO> searchProduct(String keyword);
    BatchInfoDTO searchBatchInfoById(String keyword);
    InvoiceDTO searchInvoiceById(String keyword);
    List<InvoiceDTO> searchInvoiceByDateRange(Timestamp start, Timestamp end);
    List<BatchInfoDTO> searchBatchInfoByDateRange(Timestamp start, Timestamp end);
    List<BatchInfoDTO> searchBatchInfoBySupplier(String keyword);
    BatchInfoDTO searchBatchInfoByBatchName(String keyword);
}
