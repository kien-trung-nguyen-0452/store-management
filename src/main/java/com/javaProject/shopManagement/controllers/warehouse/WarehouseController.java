package com.javaProject.shopManagement.controllers.warehouse;

import com.javaProject.shopManagement.dto.ProductDTO;
import com.javaProject.shopManagement.services.implementation.ProductServiceImpl;
import com.javaProject.shopManagement.util.effectHandler.EffectHandler;
import com.javaProject.shopManagement.util.effectHandler.EffectType;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WarehouseController {

    @FXML
    private Label productNotFoundLabel;
    @FXML
    private TextField searchBar;
    @FXML
    private  MFXButton searchButton;
    @FXML
    private  FontAwesomeIconView searchBtn;
    @FXML
    private HBox wrapper;
    @FXML
    private MFXScrollPane scrollPane;
    @FXML
    private GridPane warehouseList;

    @FXML
    private MFXProgressSpinner loadingProgressSpinner;

    private Node productDetailCard;
    private List<ProductDTO> productDTOList;
    private List<WarehouseProductCardController> productCardControllerList;

    @FXML
    protected void initialize() {
        scrollPane.setContent(warehouseList);
        scrollPane.widthProperty().addListener((observable, oldValue, newValue) -> {
                    warehouseList.setPrefWidth(newValue.doubleValue());
                    setGridPaneConstrains();
                }
        );
        refresh();
        searchButton.setOnAction(event -> search());
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
           if(searchBar.getText().isEmpty()){
               refresh();
           }

        });


    }

    public void refresh(){
        CompletableFuture.runAsync(() -> {
            productDTOList = ProductServiceImpl.getInstance().getAllProducts();
            productCardControllerList = setProductCardControllerList(productDTOList);
            loadingProgressSpinner.setVisible(true);
        }).thenAcceptAsync(productList -> {
            Platform.runLater(() -> {
                setGridPane(productCardControllerList);
                warehouseList.widthProperty().addListener((observable, oldValue, newValue) ->
                        setGridPane(productCardControllerList)
                );
                loadingProgressSpinner.setVisible(false);
                productNotFoundLabel.setVisible(productCardControllerList.isEmpty());
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
        wrapper.getChildren().remove(productDetailCard);
        productDetailCard = new ProductDetailsCardController(this, productDTO).getNode();
        EffectHandler.getEffect(EffectType.SLIDE_IN, productDetailCard);
        wrapper.getChildren().add(productDetailCard);


    }

    public void closeDetailsCard() {
        EffectHandler.getEffect(EffectType.SLIDE_OUT, productDetailCard);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            wrapper.getChildren().remove(productDetailCard);
        }));
        timeline.setCycleCount(1);
        timeline.play();


    }

    private void refresh(List<ProductDTO> productDTOList) {
        CompletableFuture.runAsync(() -> {
            this.productDTOList = productDTOList;
            productCardControllerList = setProductCardControllerList(productDTOList);
            loadingProgressSpinner.setVisible(true);
        }).thenAcceptAsync(productList -> {
            Platform.runLater(() -> {
                setGridPane(productCardControllerList);
                warehouseList.widthProperty().addListener((observable, oldValue, newValue) ->
                        setGridPane(productCardControllerList)
                );
                loadingProgressSpinner.setVisible(false);
                productNotFoundLabel.setVisible(productCardControllerList.isEmpty());
            });
        });

    }

    private void search(){
        List<ProductDTO> searchResult = new ArrayList<>();
        try {
            int productId = Integer.parseInt(searchBar.getText());
            searchResult = ProductServiceImpl.getInstance().getProductById(productId);
            System.out.println("id oke");
        }catch (NumberFormatException _){
            searchResult = ProductServiceImpl.getInstance().searchProduct(searchBar.getText());
        }
        refresh(searchResult);
    }

}

