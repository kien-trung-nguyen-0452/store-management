package com.javaProject.shopManagement.dto;

import java.sql.Timestamp;

public class ProductDTO {
    private int productId;
    private int batchId;
    private String productName;
    private double sellingPrice;
    private int quantity;
    private Timestamp expirationDate;
    private String manufacturer;
    private String imageUrl;

    public ProductDTO() {

    }

    public ProductDTO(int productId, int batchId, String productName, double sellingPrice, int quantity, Timestamp expirationDate, String manufacturer, String imageUrl) {
        this.productId = productId;
        this.batchId = batchId;
        this.productName = productName;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
        this.manufacturer = manufacturer;
        this.imageUrl = imageUrl;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "productId=" + productId +
                ", batchId=" + batchId +
                ", productName='" + productName + '\'' +
                ", sellingPrice=" + sellingPrice +
                ", quantity=" + quantity +
                ", expirationDate=" + expirationDate +
                ", manufacturer='" + manufacturer + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
