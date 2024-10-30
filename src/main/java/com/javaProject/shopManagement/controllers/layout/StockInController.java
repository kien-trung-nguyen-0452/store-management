
package com.javaProject.shopManagement.controllers.layout;

import com.javaProject.shopManagement.dto.StockInRequest;
import com.javaProject.shopManagement.services.implementation.FileServiceImpl;
import com.jfoenix.controls.JFXListView;
import io.github.palexdev.materialfx.controls.MFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

public class StockInController {

    @FXML
    public
    JFXListView<StockInRequest> importProductListViews;

    private FileServiceImpl fileServiceImpl;
    @FXML
    private Button addRow;

    @FXML
    private Button clearAll;

    private ObservableList<StockInRequest> data;

    public StockInController() {

    }

    @FXML
    public void initialize() {
        data = FXCollections.observableArrayList();
        importProductListViews.setItems(data);
        importProductListViews.setCellFactory( listView ->
            new StockInListCellController()
        );
        addRow.setOnAction(event -> {
            StockInRequest stockInRequest = new StockInRequest();
            data.add(stockInRequest);
        });

        clearAll.setOnAction(event -> {
            ObservableList<StockInRequest> current = importProductListViews.getItems();
            current.clear();
        });



    }


}

