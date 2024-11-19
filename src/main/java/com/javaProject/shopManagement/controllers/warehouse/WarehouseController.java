package com.javaProject.shopManagement.controllers.warehouse;

import com.javaProject.shopManagement.dto.ProductDTO;
import com.javaProject.shopManagement.entity.Product;
import com.javaProject.shopManagement.services.implementation.ProductServiceImpl;
import com.javaProject.shopManagement.services.interfaces.ProductService;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class WarehouseController {
    private List<ProductDTO> productDTOList;
    private List<WarehouseProductCardController> productCardControllerList;

    @FXML
    private HBox wrapper;
    @FXML
    private MFXScrollPane scrollPane;
    @FXML
    private GridPane warehouseList;


    private Node node;
    @FXML
    protected void initialize() {


        productDTOList = new ArrayList<>();
        productDTOList = ProductServiceImpl.getInstance().getAllProducts();
        productCardControllerList = setProductCardControllerList(productDTOList);
        scrollPane.setContent(warehouseList);
        scrollPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("new value: " + newValue);
            System.out.println("old value: " + oldValue);
            warehouseList.setPrefWidth(newValue.doubleValue());
        });
        warehouseList.widthProperty().addListener((observable, oldValue, newValue) ->
                setGridPane(productCardControllerList));


    }

    private List<WarehouseProductCardController> setProductCardControllerList(List<ProductDTO> productDTOList) {
        if(productDTOList == null){
            return null;
        }
        List<WarehouseProductCardController> productCardControllerList = new ArrayList<>();
        for (ProductDTO productDTO : productDTOList) {
            WarehouseProductCardController productCardController = new WarehouseProductCardController(productDTO);
            Node card = productCardController.getNode();
            card.setOnMouseClicked(mouseEvent -> getProductDetails(productDTO));
            productCardControllerList.add(productCardController);
        }
        return productCardControllerList;


    }

    public void setGridPane(List<WarehouseProductCardController> productCardControllerList) {
        warehouseList.getChildren().clear();
        warehouseList.getColumnConstraints().clear();
        warehouseList.getRowConstraints().clear();
        int productCount = productDTOList.size();
        System.out.println(productCount);
        int row = 0;
        int maxCol;
        int col =0;
        int gridWidth = (int) warehouseList.getWidth();


        System.out.println("width: "+ gridWidth);
        if (gridWidth < 1072) {
            maxCol = 3;

        } else {
            maxCol = 5;
        }
        System.out.println("Max col"+maxCol);
        for (int i = 0; i < maxCol; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            warehouseList.getColumnConstraints().add(columnConstraints);

        }
        for(WarehouseProductCardController productCardController : productCardControllerList){
            warehouseList.add(productCardController.getNode(), col, row);
            col++;
            if(col == maxCol){
                col = 0;
                row++;
                RowConstraints rowConstraints = new RowConstraints();
                warehouseList.getRowConstraints().add(rowConstraints);
            }

        }
    }

    private void getProductDetails(ProductDTO productDTO) {
        wrapper.getChildren().remove(node);
        node = new ProductDetailsCardController(this, productDTO).getNode();
        wrapper.getChildren().add(node);

    }

    public void closeDetailsCard() {
        wrapper.getChildren().remove(node);
    }
}

