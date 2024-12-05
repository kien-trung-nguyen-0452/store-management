package com.javaProject.shopManagement.dto.product;

import java.sql.Timestamp;

public class ProductStatusDTO {
    private int productId;
    private String productName;
    private int batchId;
    private Timestamp expirationDate;
    private ExpirationStatus expirationStatus;
    private String imageUrl;

    public ProductStatusDTO() {}

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

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public ExpirationStatus getExpirationStatus() {
        return expirationStatus;
    }

    public void setExpirationStatus(ExpirationStatus expirationStatus) {
        this.expirationStatus = expirationStatus;
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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
