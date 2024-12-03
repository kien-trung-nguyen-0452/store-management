package com.javaProject.shopManagement.services.implementation;

import com.javaProject.shopManagement.dao.implementation.ProductStatisticDAOImpl;
import com.javaProject.shopManagement.dao.implementation.RevenueStatisticDAOImpl;
import com.javaProject.shopManagement.dto.ProductStatisticDTO;
import com.javaProject.shopManagement.dto.RevenueStatisticDTO;
import com.javaProject.shopManagement.services.interfaces.StatisticService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class StatisticServiceImpl implements StatisticService {

    public static StatisticServiceImpl getInstance() {
        return new StatisticServiceImpl();
    }

    @Override
    public RevenueStatisticDTO getRevenueStatisticByDateRange(LocalDate dateFrom, LocalDate dateTo) {
       Timestamp start = Timestamp.valueOf(dateFrom.atStartOfDay());
       Timestamp end = Timestamp.valueOf(dateTo.atTime(23, 59, 59));
       return RevenueStatisticDAOImpl.getInstance().getRevenueStatisticOfDay(start, end);
    }

    @Override
    public RevenueStatisticDTO getDailyRevenueStatistic(LocalDate date) {
        Timestamp start = Timestamp.valueOf(date.atStartOfDay());
        Timestamp end   = Timestamp.valueOf(date.atTime(23, 59, 59));
        return RevenueStatisticDAOImpl.getInstance().getRevenueStatisticOfDay(start, end);
    }

    @Override
    public List<ProductStatisticDTO> getProductSalesOfDay(LocalDate date) {
        Timestamp start = Timestamp.valueOf(date.atStartOfDay());
        Timestamp end   = Timestamp.valueOf(date.atTime(23, 59, 59));
        return ProductStatisticDAOImpl.getInstance().getStatisticsByRange(start, end);
    }
}
