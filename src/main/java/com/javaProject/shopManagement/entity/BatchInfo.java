package com.javaProject.shopManagement.entity;

import java.sql.Timestamp;

public class BatchInfo {
    private int batchId;
    private String batchName;
    private String description;
    private String supplier;
    private double totalPrice;
    private Timestamp createDate;

    public BatchInfo() {
    }
    public BatchInfo(int batchId, String batchName, String description, String supplier, double totalPrice) {
        this.batchId = batchId;
        this.batchName = batchName;
        this.description = description;
        this.supplier = supplier;
        this.totalPrice = totalPrice;
        this.createDate = new Timestamp(System.currentTimeMillis());
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
}
