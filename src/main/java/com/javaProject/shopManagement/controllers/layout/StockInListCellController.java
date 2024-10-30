package com.javaProject.shopManagement.controllers.layout;

import com.javaProject.shopManagement.dto.StockInRequest;
import com.jfoenix.controls.JFXListCell;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class StockInListCellController extends JFXListCell<StockInRequest> {

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

    @FXML
    private HBox cellRoot;

    public StockInListCellController() {
       FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/com/javaProject/shopManagement/public/views/stockInListCell.fxml"));
       loader.setController(this);
        try{
            cellRoot = loader.load();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {

        deleteRowBtn.setOnAction(e ->{
            getListView().getItems().remove(getItem());
        });
        resetFields();


    }

    private void resetFields() {
        productIdTextField.setText("");
        productNameTextField.setText("");
        productPurchasePriceTextField.setText("");
        productSellingPriceTextField.setText("");
        productQuantityTextField.setText("");
        productManufacturerTextField.setText("");
        deleteRowBtn.setDisable(false);
    }

    @Override
    protected void updateItem(StockInRequest item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null) {
            setText(null);
            setGraphic(null);
        }
        else {
            setText(null);
            setGraphic(cellRoot);
        }
    }
}