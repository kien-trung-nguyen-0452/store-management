package com.javaProject.shopManagement.controllers.warehouse;

import com.javaProject.shopManagement.dto.ProductDTO;
import com.javaProject.shopManagement.services.implementation.ProductServiceImpl;
import com.javaProject.shopManagement.util.validator.InputValidator;
import com.javaProject.shopManagement.util.validator.logger.ErrorLogger;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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

public class ProductDetailsCardController {
    @FXML
    private AnchorPane cardRoot;

    @FXML
    private MFXButton closeBtn;
    @FXML
    private Label productNameInputMessage;
    @FXML
    private Label productPriceInputMessage;
    @FXML
    private MFXButton changeImgBtn;
    @FXML
    private ImageView productImg;
    @FXML
    private MFXTextField productPriceTextField;
    @FXML
    private MFXButton changePriceBtn;
    @FXML
    private MFXTextField productNameTextField;
    @FXML
    private MFXButton changeProductNameBtn;
    @FXML
    private MFXTextField productQuantityTextField;
    @FXML
    private MFXTextField productIdTextField;
    @FXML
    private MFXTextField batchIdTextField;
    @FXML
    private MFXTextField manufacturerTextField;
    @FXML
    private MFXTextField expirationDateTextField;
    @FXML
    private Button deleteProductBtn;
    @FXML
    private Button updateButton;
    @FXML
    private FontAwesomeIconView updateProductBtn;

    private final WarehouseController parent;

    private ProductDTO productDTO;

    public ProductDetailsCardController(WarehouseController parent, ProductDTO productDTO) {
        this.productDTO = productDTO;
        this.parent = parent;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/javaProject/shopManagement/public/views/product_details_card.fxml"));
        loader.setController(this);
        try {
            cardRoot = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void initialize() {

               productImg.setImage(new Image("file:" + productDTO.getImageUrl()));
               productNameTextField.setText(productDTO.getProductName());
               productPriceTextField.setText(String.valueOf(productDTO.getSellingPrice()));
               productQuantityTextField.setText(String.valueOf(productDTO.getQuantity()));
               productIdTextField.setText(String.valueOf(productDTO.getProductId()));
               batchIdTextField.setText(String.valueOf(productDTO.getBatchId()));
               manufacturerTextField.setText(productDTO.getManufacturer());
               expirationDateTextField.setText(String.valueOf(productDTO.getExpirationDate()).substring(0, 9));
               closeBtn.setOnAction(event -> closeBtnClick());
               deleteProductBtn.setOnAction(event -> deleteProduct());
               updateButton.setOnAction(event -> updateProduct());

    }

    public Node getNode(){
        return cardRoot;
    }

    private void closeBtnClick() {
       parent.closeDetailsCard();
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }


    private void deleteProduct() {
       if(ErrorLogger.getChoiceBox("Message", "Are you sure you want to delete the product from the inventory? These changes cannot be undone")) {
           ProductServiceImpl.getInstance().delete(productDTO.getProductId(), productDTO.getBatchId());
       }
    }
    private void updateProduct() {
        if(!update()) {
            ErrorLogger.showAlert("Invalid Input", Alert.AlertType.ERROR);
        }
        else {
            ProductServiceImpl.getInstance().update(productDTO);
            ErrorLogger.showAlert("Product Updated", Alert.AlertType.INFORMATION);
        }
    }


    private boolean update(){
        if (!validateFields()){
            return false;
        }
        productDTO.setProductName(productNameTextField.getText());
        productDTO.setSellingPrice(Double.parseDouble(productPriceTextField.getText()));
        return true;

    }
    private boolean validateFields() {
        return isValidDoubleAndNotNull(productPriceTextField, productPriceInputMessage) && isNotNull(productNameTextField, productNameInputMessage);
    }

    private boolean isValidDoubleAndNotNull(MFXTextField textField, Label message) {
        return InputValidator.isNotNull(textField, message) && InputValidator.isDouble(textField, message);
    }
    private boolean isNotNull(MFXTextField textField, Label message) {
        return InputValidator.isNotNull(textField, message);
    }
}
