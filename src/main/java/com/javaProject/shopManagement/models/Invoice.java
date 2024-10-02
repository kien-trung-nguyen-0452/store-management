package com.javaProject.shopManagement.models;

import java.sql.Timestamp;

public class Invoice {
    private int invoiceId;
    private Timestamp date;
    private double totalAmount;

    public Invoice(int invoiceId, Timestamp date, double totalAmount) {
        this.invoiceId = invoiceId;
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", date=" + date +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
