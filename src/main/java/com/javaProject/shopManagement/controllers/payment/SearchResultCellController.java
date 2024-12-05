package com.javaProject.shopManagement.controllers.payment;

import com.javaProject.shopManagement.dto.product.ProductDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class SearchResultCellController {
    @FXML
    private ImageView productImage;
    @FXML
    private HBox root;
    @FXML
    private Label productNameLabel;
    @FXML
    private Label productIdLabel;
    @FXML
    private Label productBatchIdLabel;
    @FXML
    private Label productPriceLabel;
    @FXML
    private Label productQuantityLabel;

    private final ProductDTO productDTO;

    public SearchResultCellController(ProductDTO productDTO){
        this.productDTO = productDTO;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/javaProject/shopManagement/public/views/search_result_cell.fxml"));
           loader.setController(this);
           root = loader.load();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize(){
        productNameLabel.setText(productDTO.getProductName());
        productIdLabel.setText(String.valueOf(productDTO.getProductId()));
        productBatchIdLabel.setText(String.valueOf(productDTO.getBatchId()));
        productPriceLabel.setText(String.valueOf(productDTO.getSellingPrice()));
        productQuantityLabel.setText(String.valueOf(productDTO.getQuantity()));
        productImage.setImage(new Image("file:" +productDTO.getImageUrl()));
    }



    public Node getNode(){
        return root;
    }
}
