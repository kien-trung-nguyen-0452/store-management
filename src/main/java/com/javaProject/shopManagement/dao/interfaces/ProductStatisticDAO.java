package com.javaProject.shopManagement.dao.interfaces;

import com.javaProject.shopManagement.dto.product.ProductStatisticDTO;

import java.sql.Timestamp;
import java.util.List;

public interface ProductStatisticDAO {

    List<ProductStatisticDTO> getStatisticsByRange(Timestamp startDate, Timestamp endDate);

}
