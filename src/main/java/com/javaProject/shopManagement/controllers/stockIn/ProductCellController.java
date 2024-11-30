package com.javaProject.shopManagement.controllers.stockIn;

import com.javaProject.shopManagement.dto.StockInRequest;
import com.javaProject.shopManagement.exception.GlobalExceptionHandler;
import com.javaProject.shopManagement.services.implementation.FileServiceImpl;
import com.javaProject.shopManagement.util.validator.InputValidator;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProductCellController implements Initializable {
    private final String defaultImg = "image/product_default.png";

    @FXML
    private Label productIdInputMessage;
    @FXML
    private Label productNameInputMessage;
    @FXML
    private Label purchasePriceInputMessage;
    @FXML
    private Label sellingPriceInputMessage;
    @FXML
    private Label quantityInputMessage;
    @FXML
    private Label expirationDateMessage;
    @FXML
    private ImageView productImage;
    @FXML
    private Button uploadImageBtn;
    @FXML
    private MFXTextField productIdTextField;
    @FXML
    private MFXTextField productNameTextField;
    @FXML
    private MFXTextField productPurchasePriceTextField;
    @FXML
    private MFXTextField productSellingPriceTextField;
    @FXML
    private MFXTextField productQuantityTextField;
    @FXML
    private MFXTextField productManufacturerTextField;
    @FXML
    private MFXDatePicker productExpirationDate;
    @FXML
    private Button deleteRowBtn;

    private StockInController parentController;

    @FXML
    private HBox cellRoot;

    private String productImgUrl;

    public void setParentList(StockInController parentController) {
        this.parentController = parentController;
    }

    public ProductCellController() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/javaProject/shopManagement/public/views/productCell.fxml"));
        loader.setController(this);
        try {
            cellRoot = loader.load();
        } catch (Exception e) {
            GlobalExceptionHandler.handleException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productImgUrl = defaultImg;
        productImage.setImage(new Image("file:"+ defaultImg));
        deleteRowBtn.setOnAction(_->deleteRow(parentController));
        uploadImageBtn.setOnAction(_-> uploadImage());
        setListener();

    }

    private void deleteRow(StockInController parentController){
        parentController.deleteRow(this);
    }
    private void uploadImage() {

        File imgFile =  FileServiceImpl.getInstance().chooseImageFile((Stage) uploadImageBtn.getScene().getWindow());
        try {
            String path = FileServiceImpl.getInstance().uploadTemporaryImage(imgFile);
            if(path != null) {
                productImgUrl = path;
                productImage.setImage(new Image("file:" + productImgUrl));

            }
            else {
                productImgUrl = defaultImg;
                productImage.setImage(new Image("file:" + productImgUrl));
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public Node getNode(){
        return cellRoot;
    }

    public StockInRequest getData() {
        if(!validateAll()){
            System.out.println("get data failed");
            return null;
        }
        StockInRequest data = new StockInRequest();
        data.setProductId(Integer.parseInt(productIdTextField.getText()));
        data.setProductName(productNameTextField.getText());
        data.setPurchasePrice(Double.parseDouble(productPurchasePriceTextField.getText()));
        data.setSellPrice(Double.parseDouble(productSellingPriceTextField.getText()));
        data.setQuantity(Integer.parseInt(productQuantityTextField.getText()));
        data.setExpirationDate(Timestamp.valueOf(productExpirationDate.getValue().atStartOfDay()));
        data.setImageUrl(productImgUrl);
        data.setManufacturer(productManufacturerTextField.getText());
        return data;
    }

    private void setListener(){
        productIdTextField.textProperty().addListener(_-> validateProductID());
        productNameTextField.textProperty().addListener(_ -> validateProductName());
        productQuantityTextField.textProperty().addListener(_ -> validateQuantity());
        productExpirationDate.valueProperty().addListener(_-> validateExpirationDate());
        productPurchasePriceTextField.textProperty().addListener(_-> validatePurchasePrice());
        productSellingPriceTextField.textProperty().addListener(_ -> validateSellingPrice());

    }

    public boolean validateAll(){
        return validateProductID()
                && validateProductName()
                && validatePurchasePrice()
                && validateSellingPrice()
                && validateQuantity()
                && validateExpirationDate();

    }

    private boolean validateProductID(){
        return isValidIntegerAndNotNull(productIdTextField, productIdInputMessage);
    }
    private boolean validateProductName(){
        return isNotNull(productNameTextField, productNameInputMessage);
    }
    private boolean validatePurchasePrice(){
        return isValidDoubleAndNotNull(productPurchasePriceTextField,purchasePriceInputMessage);
    }
    private boolean validateSellingPrice(){
       return isValidDoubleAndNotNull(productSellingPriceTextField, sellingPriceInputMessage);
    }
    private boolean validateQuantity(){
       return isValidIntegerAndNotNull(productQuantityTextField, quantityInputMessage);
    }
    private boolean validateExpirationDate(){
        return isValidDateAndNotNull(productExpirationDate, expirationDateMessage, LocalDate.now());
    }

    private boolean isValidIntegerAndNotNull(MFXTextField textField, Label message) {
        return InputValidator.isNotNull(textField, message) && InputValidator.isInteger(textField, message);
    }
    private boolean isValidDoubleAndNotNull(MFXTextField textField, Label message) {
        return InputValidator.isNotNull(textField, message) || InputValidator.isDouble(textField, message);
    }
    private boolean isValidDateAndNotNull(MFXDatePicker datePicker, Label message, LocalDate validationDate) {
        return InputValidator.isNotNull(datePicker, message) && InputValidator.isNotBefore(datePicker, validationDate, message);
    }
    private boolean isNotNull(MFXTextField textField, Label message) {
        return InputValidator.isNotNull(textField, message);
    }

}
