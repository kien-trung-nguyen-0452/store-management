/*
package com.javaProject.shopManagement.controllers.layout;

import com.javaProject.shopManagement.dto.ProductDTO;
import com.javaProject.shopManagement.services.implementation.FileServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

public class TableCellOfImportController extends TableCell<ProductDTO, ProductDTO> {

    @FXML
    private ImageView image;

    @FXML
    private Button changeImageButton;

    @FXML
    private TextField productId;

    @FXML
    private TextField productName;

    @FXML
    private TextField quantity;

    @FXML
    private TextField purchasePrice;

    @FXML
    private TextField manufacturer;

    @FXML
    private TextField sellingPrice;

    @FXML
    private TextField expirationDate;

    @FXML
    private Button deleteButton;

    @FXML
    private void changeImage() throws IOException {
        FileServiceImpl fileService = new FileServiceImpl();
        Stage stage = (Stage) changeImageButton.getScene().getWindow();
        String imagePath = fileService.uploadImage(fileService.chooseImageFile(stage));
        image.setImage(new ImageView(imagePath).getImage());

    }


    @FXML
    private void deleteCell() {

        System.out.println("Delete button clicked for product: " + productName.getText());
    }

    public void initialize() {

    }


    public void setCellData(ImageView image, String id, String name, String qty, String price, String manufacturer, String sellPrice, String expiration, Button deleteButton) {
        this.image = image;
        productId.setText(id);
        productName.setText(name);
        quantity.setText(qty);
        purchasePrice.setText(price);
        this.manufacturer.setText(manufacturer);
        sellingPrice.setText(sellPrice);
        expirationDate.setText(expiration);
        this.deleteButton = deleteButton;

    }
}
*/
