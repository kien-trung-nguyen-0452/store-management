package com.javaProject.shopManagement.controllers.warehouse;

import com.javaProject.shopManagement.dto.product.ProductDTO;
import com.javaProject.shopManagement.services.implementation.FileServiceImpl;
import com.javaProject.shopManagement.services.implementation.ProductServiceImpl;
import com.javaProject.shopManagement.util.logger.ErrorLogger;
import com.javaProject.shopManagement.util.validator.InputValidator;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ProductDetailsCardController {

    // FXML components
    @FXML private AnchorPane cardRoot;
    @FXML private MFXButton closeBtn;
    @FXML private MFXButton changeImgBtn;
    @FXML private MFXButton changePriceBtn;
    @FXML private MFXButton changeProductNameBtn;

    @FXML private Button updateButton;
    @FXML private Button deleteProductBtn;
    @FXML private ImageView productImg;
    @FXML private MFXTextField productNameTextField;
    @FXML private MFXTextField productPriceTextField;
    @FXML private MFXTextField productQuantityTextField;
    @FXML private MFXTextField productIdTextField;
    @FXML private MFXTextField batchIdTextField;
    @FXML private MFXTextField purchasePriceTextField;
    @FXML private MFXTextField manufacturerTextField;
    @FXML private MFXTextField expirationDateTextField;
    @FXML private Label productNameInputMessage;
    @FXML private Label productPriceInputMessage;

    // Controller dependencies
    private final WarehouseController parent;
    private ProductDTO productDTO;

    public ProductDetailsCardController(WarehouseController parent, ProductDTO productDTO) {
        this.parent = parent;
        this.productDTO = productDTO;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javaProject/shopManagement/public/views/Warehouse/product_details_card.fxml"));
        loader.setController(this);
        try {
            cardRoot = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        // Initialize fields with product data
        setTextFields();

        // Button actions
        setButtons();

    }

    public Node getNode() {
        return cardRoot;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    // Private helper methods
    private void setTextFields() {
        productImg.setImage(new Image("file:" + productDTO.getImageUrl()));
        productNameTextField.setText(productDTO.getProductName());
        productPriceTextField.setText(String.valueOf(productDTO.getSellingPrice()));
        productQuantityTextField.setText(String.valueOf(productDTO.getQuantity()));
        productIdTextField.setText(String.valueOf(productDTO.getProductId()));
        batchIdTextField.setText(String.valueOf(productDTO.getBatchId()));
        manufacturerTextField.setText(productDTO.getManufacturer());
        expirationDateTextField.setText(String.valueOf(productDTO.getExpirationDate()).substring(0, 9));
        purchasePriceTextField.setText(String.valueOf(productDTO.getPurchasePrice()));
    }

    private void setButtons(){
        closeBtn.setOnAction(event -> closeDetailsCard());
        deleteProductBtn.setOnAction(event -> deleteProduct());
        changeImgBtn.setOnAction(event -> uploadImage());
        changePriceBtn.setOnAction(event -> enablePriceEditing());
        changeProductNameBtn.setOnAction(event -> enableProductNameEditing());
        updateButton.setOnAction(event -> updateProduct());
    }

    private void closeDetailsCard() {
        parent.closeDetailsCard();
    }

    private void deleteProduct() {
        if (ErrorLogger.getChoiceBox("Message", "Are you sure you want to delete the product from the inventory? These changes cannot be undone")) {
            ProductServiceImpl.getInstance().delete(productDTO.getProductId(), productDTO.getBatchId());
            parent.refresh();
        }
    }

    private void updateProduct() {
        if (!update()) {
            ErrorLogger.showAlert("Invalid Input", Alert.AlertType.ERROR);
        } else {
            try {
                String officialUrl = FileServiceImpl.getInstance().uploadImage(productDTO.getImageUrl());
                productDTO.setImageUrl(officialUrl);
            } catch (IOException e) {
                productDTO.setImageUrl(productDTO.getImageUrl());
            }

            ProductServiceImpl.getInstance().update(productDTO);
            ErrorLogger.showAlert("Product Updated", Alert.AlertType.INFORMATION);
            parent.refresh();
        }
    }

    private void enablePriceEditing() {
        productPriceTextField.setDisable(false);
    }

    private void enableProductNameEditing() {
        productNameTextField.setDisable(false);
    }

    private void uploadImage() {
        File imgFile = FileServiceImpl.getInstance().chooseImageFile((Stage) changeImgBtn.getScene().getWindow());
        try {
            String path = FileServiceImpl.getInstance().uploadTemporaryImage(imgFile);
            if (path != null) {
                productDTO.setImageUrl(path);
                productImg.setImage(new Image("file:" + productDTO.getImageUrl()));
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private boolean update() {
        if (!validateFields()) {
            return false;
        }
        productDTO.setProductName(productNameTextField.getText());
        productDTO.setSellingPrice(Double.parseDouble(productPriceTextField.getText()));
        return true;
    }

    private boolean validateFields() {
        return isValidDoubleAndNotNull(productPriceTextField, productPriceInputMessage) &&
                isNotNull(productNameTextField, productNameInputMessage);
    }

    private boolean isValidDoubleAndNotNull(MFXTextField textField, Label message) {
        return InputValidator.isNotNull(textField, message) && InputValidator.isDouble(textField, message);
    }

    private boolean isNotNull(MFXTextField textField, Label message) {
        return InputValidator.isNotNull(textField, message);
    }
}
