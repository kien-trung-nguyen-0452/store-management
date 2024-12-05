package com.javaProject.shopManagement.dao.interfaces;

import com.javaProject.shopManagement.dto.RevenueStatisticDTO;

import java.sql.Timestamp;
import java.util.List;

public interface RevenueStatisticDAO {
   List<RevenueStatisticDTO> getRevenueStatisticsByDateRange(Timestamp start, Timestamp end);
}
