package com.javaProject.shopManagement.controllers.dashboard;

import com.javaProject.shopManagement.dto.product.ProductStatusDTO;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ProductStatusListCellController extends ListCell<ProductStatusDTO> {
    @FXML private HBox cellRoot;
    @FXML private MFXTextField productIdTextField;
    @FXML private MFXTextField batchIdTextField;
    @FXML private MFXTextField productNameTextField;
    @FXML private MFXTextField expirationDateTextField;
    @FXML private ImageView productStatusImageView;
    @FXML private ImageView productImageView;

    public ProductStatusListCellController() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/javaProject/shopManagement/public/views/Dashboard/product_status_list_cell.fxml"));
            fxmlLoader.setController(this);
            cellRoot = fxmlLoader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(ProductStatusDTO productStatusDTO, boolean b) {
        super.updateItem(productStatusDTO, b);
        if(productStatusDTO == null){
            setGraphic(null);
        }else {
            productIdTextField.setText(String.valueOf(productStatusDTO.getProductId()));
            batchIdTextField.setText(String.valueOf(productStatusDTO.getBatchId()));
            productNameTextField.setText(productStatusDTO.getProductName());
            expirationDateTextField.setText(String.valueOf(productStatusDTO.getExpirationDate()).substring(0,10));
            productImageView.setImage(new Image("file:" +productStatusDTO.getImageUrl()));
            productStatusImageView.setImage(productStatusDTO.getExpirationStatus().getImage());
            setGraphic(cellRoot);
        }
    }
}
