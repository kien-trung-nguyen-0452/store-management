package com.javaProject.shopManagement.dao.interfaces;

import com.javaProject.shopManagement.model.ProductStatistic;

import java.sql.Timestamp;
import java.util.List;

public interface ProductStatisticDAO {

    List<ProductStatistic> getStatisticsByRange(Timestamp startDate, Timestamp endDate);

}
