package com.javaProject.shopManagement.dto.product;

public class ProductStatisticDTO extends ProductDTO {
    private int productId;
    private int batchId;
    private String productName;
    private int totalSales;
    private String imageUrl;

    public ProductStatisticDTO() {
    }

    public ProductStatisticDTO(int productId, String productName, int totalSales, int batchId, String imageUrl) {
        this.productId = productId;
        this.productName = productName;
        this.totalSales = totalSales;
        this.batchId = batchId;
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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    @Override
    public String toString() {
        return "product id: " + productId + "batch id:" + batchId + ", product name: " + productName + ", total sales: " + totalSales;
    }
}
