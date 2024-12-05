package com.javaProject.shopManagement.controllers.dashboard;

import com.javaProject.shopManagement.dto.product.ProductStatisticDTO;
import com.javaProject.shopManagement.dto.RevenueStatisticDTO;
import com.javaProject.shopManagement.services.implementation.StatisticServiceImpl;
import com.javaProject.shopManagement.util.effectHandler.EffectHandler;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DailyStatisticController {

    //--general fxml
    @FXML private AnchorPane root;
    @FXML private ProgressBar revenueProgressBar;
    @FXML private ProgressBar costProgressBar;
    @FXML private ProgressBar incomeProgressBar;
    @FXML private MFXTextField revenueTextField;
    @FXML private MFXTextField costTextField;
    @FXML private MFXTextField incomeTextField;
    @FXML private MFXButton openFilterBtn;

    //--table views
    @FXML private TableView<ProductStatisticDTO> productTableView;
    @FXML private TableColumn<ProductStatisticDTO, String> idCol;
    @FXML private TableColumn<ProductStatisticDTO, String> ilusCol;
    @FXML private TableColumn<ProductStatisticDTO, String> quantityCol;
    @FXML private TableColumn<ProductStatisticDTO, String> nameCol;

    //-- Filer Pane
    @FXML private BorderPane filterPane;
    @FXML private MFXDatePicker dateDatePicker;
    @FXML private MFXButton filterBtn;
    @FXML private MFXButton resetFilterPaneBtn;
    @FXML private MFXButton closeFilterPaneBtn;
    //--local state

    private ObservableList<ProductStatisticDTO> productStatisticDTOObservableList;
    private LocalDate date;
    private RevenueStatisticDTO revenueStatisticDTO;


    public DailyStatisticController() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/javaProject/shopManagement/public/views/daily_statistic.fxml"));
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        date = LocalDate.now();
        productStatisticDTOObservableList = FXCollections.observableArrayList();
        setUI();
        setFilterPane();
    }

    private void setUI(){
        getProductStatistic();
        getRevenueStatistic();
        setTextFields();
        setProgressBars();
        setTableView();
    }

    private void getRevenueStatistic(){
        revenueStatisticDTO = StatisticServiceImpl.getInstance().getDailyRevenueStatistic(date);
    }
    private void getProductStatistic(){
        productStatisticDTOObservableList.clear();
        List<ProductStatisticDTO> productStatisticDTOS = StatisticServiceImpl.getInstance().getProductSalesOfDay(date);
        productStatisticDTOObservableList.addAll(productStatisticDTOS);
    }

    //--handle ui
    private void setTextFields(){
        revenueTextField.setText(String.valueOf(revenueStatisticDTO.getTotalRevenue()));
        costTextField.setText(String.valueOf(revenueStatisticDTO.getTotalCost()));
        incomeTextField.setText(String.valueOf(revenueStatisticDTO.getIncome()));
    }
    private void setProgressBars(){
        double cost = revenueStatisticDTO.getTotalCost();
        double revenue = revenueStatisticDTO.getTotalRevenue();
        double income  = revenueStatisticDTO.getIncome();
        List<Double> list = new ArrayList<>(Arrays.asList(cost, revenue, income));
        double max = Collections.max(list);

        costProgressBar.setProgress(cost/max);
        revenueProgressBar.setProgress(revenue/max);
        if(income>=0){
        incomeProgressBar.setProgress(income/max);}
        else {
            incomeProgressBar.setProgress(0);
        }
    }
    //--handle table view
    private void setTableView(){
        idCol.setCellValueFactory(product -> new SimpleObjectProperty<>(String.valueOf(product.getValue().getProductId())));
        ilusCol.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getImageUrl()));
        ilusCol.setCellFactory(column -> new TableCell<ProductStatisticDTO, String>(){
            @Override
            protected void updateItem(String s, boolean b) {
                super.updateItem(s, b);
                if(s == null){
                    setGraphic(null);
                } else {
                 ImageView imageview = new ImageView();
                 imageview.setFitHeight(30);
                 imageview.setFitWidth(30);
                 imageview.setImage(new Image("file:"+s));
                 setGraphic(imageview);
            }
            }

        });
        quantityCol.setCellValueFactory(product -> new SimpleStringProperty(String.valueOf(product.getValue().getTotalSales())));
        nameCol.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getProductName()));
        productTableView.setItems(productStatisticDTOObservableList);

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
        date = dateDatePicker.getValue();
        setUI();
    }




    public Node getNode(){
        return root;
    }





}
