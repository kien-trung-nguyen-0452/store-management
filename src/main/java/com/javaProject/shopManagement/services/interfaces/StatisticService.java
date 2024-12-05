package com.javaProject.shopManagement.services.interfaces;

import com.javaProject.shopManagement.dto.product.ProductStatisticDTO;
import com.javaProject.shopManagement.dto.RevenueStatisticDTO;
import com.javaProject.shopManagement.dto.product.ProductStatusDTO;

import java.time.LocalDate;
import java.util.List;

public interface StatisticService {
    List<RevenueStatisticDTO> getRevenueStatisticByDateRange(LocalDate dateFrom, LocalDate dateTo);
    RevenueStatisticDTO getDailyRevenueStatistic(LocalDate date);
    List<ProductStatisticDTO> getProductSalesOfDay(LocalDate date);
    List<ProductStatusDTO> getExpiringProduct(int dateRange);
    List<ProductStatusDTO> getExpiredProduct();


}
