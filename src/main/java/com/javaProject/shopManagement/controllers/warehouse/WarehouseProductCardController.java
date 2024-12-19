package com.javaProject.shopManagement.controllers.warehouse;

import com.javaProject.shopManagement.dto.product.ProductDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class WarehouseProductCardController {

    @FXML
    private Label productSellingPrice;
    @FXML
    private ImageView productImage;
    @FXML
    private AnchorPane cardRoot;
    @FXML
    private Label productName;
    @FXML
    private Label productQuantity;


    private final ProductDTO productDTO;

    public WarehouseProductCardController(ProductDTO productDTO){
        this.productDTO = productDTO;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/javaProject/shopManagement/public/views/Warehouse/warehouse_product_card.fxml"));
        loader.setController(this);
        try {
            cardRoot = loader.load();
            cardRoot.setScaleX(0.9);
            cardRoot.setScaleY(0.9);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        productImage.setImage(new Image("file:"+productDTO.getImageUrl()));
        productName.setText(productDTO.getProductName());
        productQuantity.setText(String.valueOf(productDTO.getQuantity()));
        productSellingPrice.setText(String.valueOf(productDTO.getSellingPrice()) + "$");


    }

    public Node getNode(){
        return cardRoot;
    }

}
