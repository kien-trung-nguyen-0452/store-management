package com.javaProject.shopManagement.controllers.dashboard;

import com.javaProject.shopManagement.dto.ProductStatisticDTO;
import com.javaProject.shopManagement.dto.RevenueStatisticDTO;
import com.javaProject.shopManagement.services.implementation.StatisticServiceImpl;
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

import java.time.LocalDate;
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

    //--table views
    @FXML private TableView<ProductStatisticDTO> productTableView;
    @FXML private TableColumn<ProductStatisticDTO, String> idCol;
    @FXML private TableColumn<ProductStatisticDTO, String> ilusCol;
    @FXML private TableColumn<ProductStatisticDTO, String> quantityCol;

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
        revenueProgressBar.setProgress(1.0);
        costProgressBar.setProgress(revenueStatisticDTO.getTotalCost()/revenueStatisticDTO.getTotalRevenue());
        incomeProgressBar.setProgress(revenueStatisticDTO.getIncome()/revenueStatisticDTO.getTotalCost());
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
        productTableView.setItems(productStatisticDTOObservableList);

    }



    public Node getNode(){
        return root;
    }





}
