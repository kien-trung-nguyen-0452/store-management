package com.javaProject.shopManagement.services.implementation;

import com.javaProject.shopManagement.dto.BatchInfoDTO;
import com.javaProject.shopManagement.dto.InvoiceDTO;
import com.javaProject.shopManagement.dto.product.ProductDTO;
import com.javaProject.shopManagement.services.interfaces.SearchService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SearchServiceImpl implements SearchService {

    public static SearchServiceImpl getInstance(){
        return new SearchServiceImpl();
    }
    @Override
    public List<ProductDTO> searchProduct(String keyword) {
        List<ProductDTO> searchResult = new ArrayList<>();
        try {
            int productId = Integer.parseInt(keyword);
            searchResult = ProductServiceImpl.getInstance().getProductById(productId);
        }catch (NumberFormatException _){
            searchResult = ProductServiceImpl.getInstance().searchProduct(keyword);
        }
        return searchResult;
    }

    @Override
    public BatchInfoDTO searchBatchInfoById(String keyword) {
       try {
           int batchId = Integer.parseInt(keyword);
           return BatchInfoServiceImpl.getInstance().getById(batchId);
       }catch (NumberFormatException _){
           return null;
       }

    }

    @Override
    public InvoiceDTO searchInvoiceById(String keyword) {
        try{
            int invoiceId = Integer.parseInt(keyword);
            return InvoiceServiceImpl.getInstance().getInvoice(invoiceId);
        }catch (NumberFormatException _){
            return null;
        }
    }

    @Override
    public List<InvoiceDTO> searchInvoiceByDateRange(Timestamp start, Timestamp end) {
        return InvoiceServiceImpl.getInstance().getInvoicesByDateRange(start, end);
    }

    @Override
    public List<BatchInfoDTO> searchBatchInfoByDateRange(Timestamp start, Timestamp end) {
        return BatchInfoServiceImpl.getInstance().getBatchInfoByDateRange(start, end);
    }

    @Override
    public List<BatchInfoDTO> searchBatchInfoBySupplier(String keyword) {
        return BatchInfoServiceImpl.getInstance().getBatchInfoBySupplier(keyword);
    }

    @Override
    public BatchInfoDTO searchBatchInfoByBatchName(String keyword) {
        return BatchInfoServiceImpl.getInstance().getBatchInfoByBatchName(keyword);
    }

}
