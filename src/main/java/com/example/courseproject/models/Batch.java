package com.example.courseproject.models;

import java.sql.Date;

public class Batch {
    private int id;
    private String batchName;
    private int productId;
    private String productName;
    private int quantity;
    private Date expirationDate;
    private Date createDate;

    public Batch(int id, String batchName, int productId, String productName, int quantity, Date expirationDate, Date createDate) {
        this.id = id;
        this.batchName = batchName;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
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

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
