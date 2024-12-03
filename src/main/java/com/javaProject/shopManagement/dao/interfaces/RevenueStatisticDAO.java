package com.javaProject.shopManagement.dao.interfaces;

import com.javaProject.shopManagement.dto.RevenueStatisticDTO;

import java.sql.Timestamp;

public interface RevenueStatisticDAO {
   RevenueStatisticDTO getRevenueStatisticOfDay(Timestamp start, Timestamp end);
}
