package com.example.courseproject.models;

import java.sql.Date;

public class SalesInvoice {
    private int id;
    private int productId;
    private String productName;
    private int quantity;
    private double price;
    private Date date;
    private int batchId;

    public SalesInvoice(int batchId, Date date, double price, int quantity, String productName, int productId, int id) {
        this.batchId = batchId;
        this.date = date;
        this.price = price;
        this.quantity = quantity;
        this.productName = productName;
        this.productId = productId;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }
}
