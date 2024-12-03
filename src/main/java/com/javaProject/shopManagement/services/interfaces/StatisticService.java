package com.javaProject.shopManagement.services.interfaces;

import com.javaProject.shopManagement.dto.ProductStatisticDTO;
import com.javaProject.shopManagement.dto.RevenueStatisticDTO;

import java.time.LocalDate;
import java.util.List;

public interface StatisticService {


    RevenueStatisticDTO getRevenueStatisticByDateRange(LocalDate dateFrom, LocalDate dateTo);
    RevenueStatisticDTO getDailyRevenueStatistic(LocalDate date);
    List<ProductStatisticDTO> getProductSalesOfDay(LocalDate date);


}
