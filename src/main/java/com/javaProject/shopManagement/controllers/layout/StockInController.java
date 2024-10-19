package com.javaProject.shopManagement.controllers.layout;

import com.javaProject.shopManagement.dto.ProductDTO;
import com.javaProject.shopManagement.services.implementation.FileServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class StockInController {

    @FXML
    private TableView<ProductDTO> tableView;

    @FXML
    private TableColumn<ProductDTO, ProductDTO> illustrationColumn;

    private FileServiceImpl fileServiceImpl;

    public StockInController() {

    }

    @FXML
    public void initialize() {

        illustrationColumn.setCellFactory(new Callback<TableColumn<ProductDTO, ProductDTO>, TableCell<ProductDTO, ProductDTO>>() {
            @Override
            public TableCell<ProductDTO, ProductDTO> call(TableColumn<ProductDTO, ProductDTO> param) {
                return new TableCellOfImportController();
            }
        });

        tableView.setItems((ObservableList<ProductDTO>) new ProductDTO());
    }
}