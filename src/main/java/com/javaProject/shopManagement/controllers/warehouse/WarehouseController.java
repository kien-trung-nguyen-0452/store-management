package com.javaProject.shopManagement.controllers.warehouse;

import com.javaProject.shopManagement.dto.ProductDTO;
import com.javaProject.shopManagement.services.implementation.ProductServiceImpl;
import com.javaProject.shopManagement.util.effectHandler.EffectHandler;
import com.javaProject.shopManagement.util.effectHandler.EffectType;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
        scrollPane.setContent(warehouseList);
        scrollPane.widthProperty().addListener((observable, oldValue, newValue) -> {
                    warehouseList.setPrefWidth(newValue.doubleValue());
                    setGridPaneConstrains();
                }
        );
        CompletableFuture.runAsync(() -> {
            productDTOList = ProductServiceImpl.getInstance().getAllProducts();
            productCardControllerList = setProductCardControllerList(productDTOList);


        }).thenAcceptAsync(productList -> {
            Platform.runLater(() -> {
                setGridPane(productCardControllerList);
                warehouseList.widthProperty().addListener((observable, oldValue, newValue) ->
                            setGridPane(productCardControllerList)
                );
            });
        });
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
        int row = 0;
        int col =0;
        setGridPaneConstrains();
        for(WarehouseProductCardController productCardController : productCardControllerList){
            warehouseList.add(productCardController.getNode(), col, row);
            col++;
            if(col == warehouseList.getColumnConstraints().size()){
                col = 0;
                row++;
                RowConstraints rowConstraints = new RowConstraints();
                warehouseList.getRowConstraints().add(rowConstraints);
            }

        }
    }

    private void setGridPaneConstrains(){
        warehouseList.getChildren().clear();
        warehouseList.getColumnConstraints().clear();
        warehouseList.getRowConstraints().clear();
        double gridWidth = warehouseList.getPrefWidth();
        int maxCol =0;

        if (gridWidth >= 1072.0 && gridWidth <1500) {
            maxCol = 5;

        }
        else if(gridWidth > 1500){
            maxCol = 6;
        }
        else {
            maxCol = 3;
        }
        for (int i = 0; i < maxCol; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            warehouseList.getColumnConstraints().add(columnConstraints);

        }
    }

    private void getProductDetails(ProductDTO productDTO) {
        wrapper.getChildren().remove(node);
        node = new ProductDetailsCardController(this, productDTO).getNode();
        EffectHandler.getEffect(EffectType.SLIDE_IN, node);
        wrapper.getChildren().add(node);


    }

    public void closeDetailsCard() {
        EffectHandler.getEffect(EffectType.SLIDE_OUT, node);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            wrapper.getChildren().remove(node);
        }));
        timeline.setCycleCount(1);
        timeline.play();


    }

}

