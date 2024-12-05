package com.javaProject.shopManagement.dto;

import java.sql.Timestamp;

public class RevenueStatisticDTO {
    double totalRevenue;
    double totalCost;
    String date;


    public RevenueStatisticDTO(double totalRevenue, double totalCost, String date) {
        this.totalRevenue = totalRevenue;
        this.totalCost = totalCost;
        this.date = date;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getIncome(){
        return totalRevenue - totalCost;
    }
}
