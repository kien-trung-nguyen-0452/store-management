
package com.javaProject.shopManagement.controllers.stockIn;

import com.javaProject.shopManagement.dto.BatchInfoDTO;
import com.javaProject.shopManagement.dto.StockInRequest;
import com.javaProject.shopManagement.mapper.StockInRequestMapper;
import com.javaProject.shopManagement.services.implementation.BatchInfoServiceImpl;
import com.javaProject.shopManagement.services.implementation.BatchServiceImpl;
import com.javaProject.shopManagement.services.implementation.ProductServiceImpl;
import com.javaProject.shopManagement.util.validator.InputValidator;
import com.javaProject.shopManagement.util.effectHandler.EffectHandler;
import com.javaProject.shopManagement.util.effectHandler.EffectType;
import com.javaProject.shopManagement.util.logger.ErrorLogger;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.sql.Timestamp;
import java.util.HashSet;


public class StockInController{

    @FXML
    private MFXTextField batchCodeTextField;
    @FXML
    private MFXTextField supplierTextField;
    @FXML
    private Label batchCodeMessage;
    @FXML
    private MFXDatePicker batchCreateDate;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Label batchNameMessage;
    @FXML
    private MFXTextField batchNameTextField;
    @FXML
    private Button addNewRequest;
    @FXML
    private AnchorPane lowerPane;
    @FXML
    private AnchorPane upperPane;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private VBox productList;
    @FXML
    private Button addRow;

    @FXML
    private Button clearAll;


    @FXML
    private Button getTotalPriceBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button importBtn;

    private double totalPrice;
    private ObservableList<ProductCellController> cells;
    private HashSet<StockInRequest> stockInRequestList;

    public StockInController() {

    }

    @FXML
    private void initialize() {
        lowerPane.setDisable(true);
        upperPane.setDisable(true);
        addNewRequest.setOnAction(_-> addNewRequest());
        addRow.setOnAction(_ -> addRow());
        clearAll.setOnAction(_-> clearAll());
        cells = FXCollections.observableArrayList();
        stockInRequestList = new HashSet<>();
        importBtn.setOnAction(_ -> importStockIn());
        getTotalPriceBtn.setOnAction(_-> {
            checkValidate();
            getTotalPrice();});
        cancelBtn.setOnAction(_-> cancel());
        setListener();


    }
    private void addNewRequest() {
        lowerPane.setDisable(false);
        upperPane.setDisable(false);
        addNewRequest.setDisable(true);
    }

    private void addRow(){
        ProductCellController cell = new ProductCellController();
        productList.getChildren().add(cell.getNode());
        cell.setParentList(this);
        cells.add(cell);
        EffectHandler.getEffect(EffectType.FADE_IN, cell.getNode());

    }
    public void deleteRow(ProductCellController cell){

        EffectHandler.getEffect(EffectType.FADE_OUT, cell.getNode());
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            productList.getChildren().remove(cell.getNode());
            cells.remove(cell);
        }));
        timeline.setCycleCount(1);
        timeline.play();



    }
    private void clearAll(){
        productList.getChildren().clear();
        cells.clear();
    }

    public void getTotalPrice(){
        totalPrice = 0.0;
        for(StockInRequest stockInRequest : stockInRequestList){
            totalPrice += stockInRequest.getQuantity() * stockInRequest.getPurchasePrice();
        }
        String formattedTotalPrice = String.format("%.3f", totalPrice);
        totalPriceLabel.setText(formattedTotalPrice + "$");
    }


    private void importStockIn(){
        BatchInfoDTO batchInfoDTO = getBatchInfo();
         if(batchInfoDTO == null){
           ErrorLogger.showAlert("Invalid batch information", Alert.AlertType.ERROR);
         }
         else if (checkValidate()) {
               getTotalPrice();
               batchInfoDTO.setTotalPrice(totalPrice);
               BatchInfoServiceImpl.getInstance().add(batchInfoDTO);
               BatchServiceImpl batchServiceImpl = new BatchServiceImpl();
               ProductServiceImpl productService = new ProductServiceImpl();
               for (StockInRequest stockInRequest : stockInRequestList) {
                   batchServiceImpl.addBatch(StockInRequestMapper.mapToBatchDTO(stockInRequest, batchInfoDTO));
                   productService.add(StockInRequestMapper.mapToProductDTO(stockInRequest, batchInfoDTO));
               }
       }


    }

    private void cancel (){
       batchCodeTextField.clear();
       supplierTextField.clear();
       batchCreateDate.clear();
       batchNameTextField.clear();
       descriptionTextArea.clear();
       clearAll();
       upperPane.setDisable(true);
       lowerPane.setDisable(true);
       getTotalPriceBtn.setDisable(true);
       totalPriceLabel.setText("0.0$");
       addNewRequest.setDisable(false);
    }



    private boolean checkValidate() {
        stockInRequestList.clear();
        for (ProductCellController cell : cells) {
            resetCellStyleClass(cell);
            if(cell.getData()==null){
                ErrorLogger.showAlert("Invalid input", Alert.AlertType.ERROR);
                cell.getNode().getStyleClass().add("error");
                stockInRequestList.clear();
                return false;
            }
            if(stockInRequestList.contains(cell.getData())){
                ErrorLogger.showAlert("Duplicate product Id", Alert.AlertType.WARNING);
                cell.getNode().getStyleClass().add("warning");
                stockInRequestList.clear();
               return false;
            }
            else {
                stockInRequestList.add(cell.getData());
            }

        }
        return true;
    }
    
    private void setListener(){
        batchCodeTextField.textProperty().addListener(_-> validateBatchCode());
        batchNameTextField.textProperty().addListener(_-> validateBatchName());
    }

    private BatchInfoDTO getBatchInfo(){
        if (!validateBatchCode()&&!validateBatchName()){
            return null;
        }

        if (BatchInfoServiceImpl.getInstance().getById(Integer.parseInt(batchCodeTextField.getText())) != null) {
            ErrorLogger.showAlert("Batch already exist", Alert.AlertType.WARNING);
            return null;
        }
        BatchInfoDTO batchInfoDTO = new BatchInfoDTO();
        batchInfoDTO.setBatchId(Integer.parseInt(batchCodeTextField.getText()));
        batchInfoDTO.setSupplier(supplierTextField.getText());
        batchInfoDTO.setBatchName(batchNameTextField.getText());
        batchInfoDTO.setDescription(descriptionTextArea.getText());
        if (batchCreateDate.getValue() != null) {
            batchInfoDTO.setCreateDate(Timestamp.valueOf(batchCreateDate.getValue().atStartOfDay()));
        }
        else {
            batchInfoDTO.setCreateDate(new Timestamp(System.currentTimeMillis()));
        }
        return batchInfoDTO;
    }

    private boolean validateBatchCode(){
        return isValidIntegerAndNotNull(batchCodeTextField, batchCodeMessage);
    }
    private boolean validateBatchName(){
        return isNotNull(batchNameTextField, batchNameMessage);
    }

    private boolean isValidIntegerAndNotNull(MFXTextField textField, Label message) {
        return InputValidator.isNotNull(textField, message) && InputValidator.isInteger(textField, message);
    }

    private boolean isNotNull(MFXTextField textField, Label message) {
        return InputValidator.isNotNull(textField, message);
    }

    private void resetCellStyleClass(ProductCellController cell) {
        cell.getNode().getStyleClass().remove("error");
        cell.getNode().getStyleClass().remove("warning");
    }

}





