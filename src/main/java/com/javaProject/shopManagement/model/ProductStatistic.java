package com.javaProject.shopManagement.model;

import java.sql.Timestamp;

public class ProductStatistic {
    private int productId;
    private String productName;
    private int totalSales;
    private String imageUrl;

    public ProductStatistic() {
    }

    public ProductStatistic(int productId, String productName, int totalSales, String imageUrl) {
        this.productId = productId;
        this.productName = productName;
        this.totalSales = totalSales;
        this.imageUrl = imageUrl;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
