package com.javaProject.shopManagement.models;

import java.sql.Timestamp;

public class Batch {
    private int batchId;
    private int productId;
    private String productName;
    private int quantity;
    private double purchasePrice;
    private Timestamp createDate;
    private String supplierName;

    public Batch(int batchId, String supplierName, Timestamp createDate, int quantity, double purchasePrice,  String productName, int productId) {
        this.batchId = batchId;
        this.supplierName = supplierName;
        this.createDate = createDate;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.productName = productName;
        this.productId = productId;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "batchId=" + batchId +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", purchasePrice=" + purchasePrice +
                ", createDate=" + createDate +
                ", supplierName='" + supplierName + '\'' +
                '}';
    }
}
