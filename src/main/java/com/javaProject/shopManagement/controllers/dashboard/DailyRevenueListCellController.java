package com.javaProject.shopManagement.controllers.dashboard;

import com.javaProject.shopManagement.dto.RevenueStatisticDTO;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

import java.text.DecimalFormat;

public class DailyRevenueListCellController extends ListCell<RevenueStatisticDTO> {
    @FXML private HBox cellRoot;
    @FXML private MFXTextField dateTextField;
    @FXML private MFXTextField revenueTextField;
    @FXML private MFXTextField incomeTextField;
    @FXML private MFXTextField proportionTextField;

    private RevenueStatisticDTO data;

    public DailyRevenueListCellController() {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/javaProject/shopManagement/public/views/daily_revenue_list_cell.fxml"));
            loader.setController(this);
            cellRoot = loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void updateItem(RevenueStatisticDTO revenueStatisticDTO, boolean b) {
        super.updateItem(revenueStatisticDTO, b);
        if(revenueStatisticDTO == null){
            setGraphic(null);
        }
        else {
            double revenue = revenueStatisticDTO.getTotalRevenue();
            double income = revenueStatisticDTO.getIncome();
            double proportion = income/revenue;
            DecimalFormat df = new DecimalFormat("#0.0%");
            dateTextField.setText(revenueStatisticDTO.getDate());
            revenueTextField.setText(String.valueOf(new DecimalFormat("#0.00").format(revenue)));
            if(income<0) {
               incomeTextField.setStyle("-fx-text-fill: red;");
            }
            else {
                incomeTextField.setStyle("-fx-text-fill: green;");
            }
            incomeTextField.setText(String.valueOf(new DecimalFormat("#0.00").format(income)));
            if(proportion<0) {
                proportionTextField.setStyle("-fx-text-fill: red;");
            }else {
                proportionTextField.setStyle("-fx-text-fill: green;");
            }

            proportionTextField.setText(new DecimalFormat("#0.0%").format(proportion));

            setGraphic(cellRoot);
        }
    }
}
