package com.javaProject.shopManagement.controllers.dashboard;

import com.javaProject.shopManagement.dto.RevenueStatisticDTO;
import com.javaProject.shopManagement.dto.product.ProductStatusDTO;
import com.javaProject.shopManagement.services.implementation.StatisticServiceImpl;
import com.javaProject.shopManagement.util.effectHandler.EffectHandler;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

public class GeneralStatisticController {


    //--general fxml component
    @FXML
    private AnchorPane root;
    @FXML
    private MFXTextField totalIncomeTextField;
    @FXML
    private MFXTextField totalRevenueTextField;
    @FXML
    private MFXTextField proportionTextField;
    @FXML
    private AreaChart<String, Double> revenueChart;
    @FXML private MFXButton openFilterBtn;
    @FXML
    private ListView<RevenueStatisticDTO> dailyRevenueListView;

    //-- Filer Pane
    @FXML private BorderPane filterPane;
    @FXML private MFXDatePicker dateDatePicker;
    @FXML private MFXButton filterBtn;
    @FXML private MFXButton resetFilterPaneBtn;
    @FXML private MFXButton closeFilterPaneBtn;

    //--product status
    @FXML private ListView<ProductStatusDTO> expiringProductListView;
    @FXML private ListView<ProductStatusDTO> expiredProductListView;

    //--local state
    private final int CLOSE_TO_EXPIRY_DAYS = 7;
    private LocalDate startDate;
    private LocalDate endDate;
    private ObservableList<RevenueStatisticDTO> revenueStatistics;
    private ObservableList<ProductStatusDTO> expiredProductStatistics;
    private ObservableList<ProductStatusDTO> expiringProductStatistics;
    private  double totalRevenue;
    private double totalProportion;
    private  double totalIncome;



    //--constructor
    public GeneralStatisticController() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(GeneralStatisticController.class.getResource("/com/javaProject/shopManagement/public/views/general_statistic.fxml"));
            fxmlLoader.setController(this);
            root = fxmlLoader.load();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void initialize() {
        startDate = LocalDate.now().withDayOfMonth(1);
        endDate = LocalDate.now();
        revenueStatistics = FXCollections.observableArrayList();
        expiredProductStatistics = FXCollections.observableArrayList();
        expiringProductStatistics = FXCollections.observableArrayList();
        setFilterPane();
        setUI();
        setRevenueListView();
        setProductStatisticListView();
    }

    private void setUI(){
        setData();
        setChart();
        setTextField();
    }

    private void setData() {
        revenueStatistics.clear();
        List<RevenueStatisticDTO> list = StatisticServiceImpl.getInstance().getRevenueStatisticByDateRange(startDate, endDate);
        revenueStatistics.addAll(list);
    }

    //--area chart
    private void setChart() {
        revenueChart.getData().clear();
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        for (RevenueStatisticDTO revenueStatistic : revenueStatistics) {
            series.getData().add(new XYChart.Data<>(revenueStatistic.getDate(), revenueStatistic.getTotalRevenue()));
        }
        revenueChart.getData().add(series);
    }

    //--Total revenue, income, proportion
    private void getTotalRevenue() {
        totalRevenue = 0;
        for( RevenueStatisticDTO revenueStatistic : revenueStatistics){
            totalRevenue+=revenueStatistic.getTotalRevenue();
        }
    }
    private void getTotalIncome() {
        totalIncome = 0;
        for( RevenueStatisticDTO revenueStatistic : revenueStatistics){
            totalIncome+= revenueStatistic.getIncome();
        }
    }
    private void getTotalProportion() {
        totalProportion = totalIncome / totalRevenue;
    }

    private void setTextFieldListener(){
        totalIncomeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(totalIncome <0){
                totalIncomeTextField.setStyle("-fx-text-fill: red");
            }else{
                totalIncomeTextField.setStyle("-fx-text-fill: green");
            }
        });
        proportionTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(totalProportion <0){
                proportionTextField.setStyle("-fx-text-fill: red");
            }else {
                proportionTextField.setStyle("-fx-text-fill: green");
            }
        });
    }

    private void setTextField(){
        getTotalRevenue();
        getTotalIncome();
        getTotalProportion();
        setTextFieldListener();
        totalRevenueTextField.setText(String.valueOf(new DecimalFormat("0.00").format(totalRevenue)));
        totalIncomeTextField.setText(String.valueOf(new DecimalFormat("#0.00").format(totalIncome)));
        proportionTextField.setText(new DecimalFormat("#0.0%").format(totalProportion));

    }

    //-- handle filter pane
    //-- filter pane
    private void setFilterPane(){
        openFilterBtn.setOnAction(e->openFilterPane());
        resetFilterPaneBtn.setOnAction(e->resetFilterPane());
        closeFilterPaneBtn.setOnAction(e->{
            resetFilterPane();
            closeFilterPane();});
        filterBtn.setOnAction(e->{
            filterDate();
        });

    }
    private void openFilterPane(){
        EffectHandler.setShowUP(filterPane, filterPane.isVisible());
    }
    private void closeFilterPane(){
        filterPane.setVisible(false);
    }
    private void resetFilterPane(){
        dateDatePicker.clear();

    }
    private void filterDate(){
       startDate = dateDatePicker.getValue();
       setUI();

    }

    //--set revenue list view
    private void setRevenueListView(){
        dailyRevenueListView.setItems(revenueStatistics);
        dailyRevenueListView.setCellFactory(revenueStatisticDTOListView -> new DailyRevenueListCellController()
        );

    };

    //--set product status list view

    private void setProductStatisticListView(){
        setExpiredProductListView();
        setExpiringProductListView();
    }
    private void setExpiredProductListView(){
        expiredProductStatistics.addAll(StatisticServiceImpl.getInstance().getExpiredProduct());
        expiredProductListView.setCellFactory(productStatusDTOListView -> new ProductStatusListCellController());
        expiredProductListView.setItems(expiredProductStatistics);
    }
    private void setExpiringProductListView(){
        expiringProductStatistics.addAll(StatisticServiceImpl.getInstance().getExpiringProduct(CLOSE_TO_EXPIRY_DAYS));
        expiringProductListView.setCellFactory(productStatusDTOListView -> new ProductStatusListCellController());
        expiringProductListView.setItems(expiringProductStatistics);
    }



    public Node getNode(){
        return root;
    }
}
