package com.javaProject.shopManagement.dto;

public class RevenueStatisticDTO {
    double totalRevenue;
    double totalCost;

    public RevenueStatisticDTO(double totalRevenue, double totalCost) {
        this.totalRevenue = totalRevenue;
        this.totalCost = totalCost;
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
