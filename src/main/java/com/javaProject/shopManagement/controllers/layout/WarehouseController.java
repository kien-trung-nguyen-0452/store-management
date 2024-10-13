package com.javaProject.shopManagement.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;

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

    // Các phương thức khởi tạo
    @FXML
    public void initialize() {
        productDeleteButton.setOnAction(event -> handleDeleteProduct());
        productUpdateButton.setOnAction(event -> handleUpdateProduct());
    }

    // Phương thức xử lý sự kiện nút Xóa
    private void handleDeleteProduct() {
        // Logic để xóa sản phẩm
        System.out.println("Deleting product: " + productName.getText());
        // Xử lý xóa sản phẩm ở đây
    }
    private void handleUpdateProduct() {

        System.out.println("Updating product: " + productName.getText());

    }


}
