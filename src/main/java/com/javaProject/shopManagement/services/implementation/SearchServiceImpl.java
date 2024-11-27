package com.javaProject.shopManagement.services.implementation;

import com.javaProject.shopManagement.dto.ProductDTO;
import com.javaProject.shopManagement.entity.Product;
import com.javaProject.shopManagement.services.interfaces.SearchService;

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
            System.out.println("id oke");
        }catch (NumberFormatException _){
            searchResult = ProductServiceImpl.getInstance().searchProduct(keyword);
        }
        return searchResult;
    }
}
