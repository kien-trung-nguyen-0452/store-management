package com.javaProject.shopManagement.controllers.layout;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class WarehouseController {
    // Các thành phần UI từ FXML
    @FXML
    private TextField productSellingPrice;

    @FXML
    private Label productName;

    @FXML
    private Label productPurchasePrice;

    @FXML
    private Label productId;

    @FXML
    private Label productBatchId;

    @FXML
    private Label productExpirationDate;

    @FXML
    private Label productPurchaseDate;

    @FXML
    private Label productManufacturer;

    @FXML
    private Label productSupplier;

    @FXML
    private Label productQuantity;

    @FXML
    private ImageView productImage;


    @FXML
    private Button productDeleteButton;

    @FXML
    private Button productUpdateButton;

    @FXML
    public void initialize() {
        productDeleteButton.setOnAction(event -> handleDeleteProduct());
        productUpdateButton.setOnAction(event -> handleUpdateProduct());
    }

    private void handleDeleteProduct() {
        System.out.println("Deleting product: " + productName.getText());

    }
    private void handleUpdateProduct() {

        System.out.println("Updating product: " + productName.getText());

    }


}
