
package com.javaProject.shopManagement.controllers.stockIn;

import com.javaProject.shopManagement.dto.BatchInfoDTO;
import com.javaProject.shopManagement.dto.StockInRequest;
import com.javaProject.shopManagement.services.implementation.BatchInfoServiceImpl;
import com.javaProject.shopManagement.services.implementation.FileServiceImpl;
import com.javaProject.shopManagement.services.implementation.StockInServiceImpl;
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

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;


public class StockInController {

    @FXML
    private MFXTextField batchCodeTextField, supplierTextField, batchNameTextField;
    @FXML
    private MFXDatePicker batchCreateDate;
    @FXML
    private Label batchCodeMessage, batchNameMessage, totalPriceLabel;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private VBox productList;
    @FXML
    private AnchorPane lowerPane, upperPane;
    @FXML
    private Button addNewRequest, addRow, clearAll, getTotalPriceBtn, cancelBtn, importBtn;

    private double totalPrice;
    private List<ProductCellController> cells;
    private HashSet<StockInRequest> stockInRequestList;

    private static StockInController instance;

    public static StockInController getInstance() {
        if (instance == null) {
            instance = new StockInController();
        }
        return instance;
    }
    @FXML
    private void initialize() {
        lowerPane.setDisable(true);
        upperPane.setDisable(true);
        cells = FXCollections.observableArrayList();
        stockInRequestList = new HashSet<>();

        setupEventHandlers();
        setListener();
    }

    private void setupEventHandlers() {
        addNewRequest.setOnAction(event -> enableStockInUI());
        addRow.setOnAction(event -> addRow());
        clearAll.setOnAction(event -> clearAll());
        getTotalPriceBtn.setOnAction(event -> {
            if (checkValidate()) {
                getTotalPrice();
            }
        });
        cancelBtn.setOnAction(event -> cancel());
        importBtn.setOnAction(event -> {
            try {
                importStockIn();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // --- UI ---
    private void enableStockInUI() {
        lowerPane.setDisable(false);
        upperPane.setDisable(false);
        addNewRequest.setDisable(true);
    }

    private void addRow() {
        ProductCellController cell = new ProductCellController();
        productList.getChildren().add(cell.getNode());
        cell.setParentList(this);
        cells.add(cell);
        EffectHandler.getEffect(EffectType.FADE_IN, cell.getNode());
    }

    public void deleteRow(ProductCellController cell) {
        EffectHandler.getEffect(EffectType.FADE_OUT, cell.getNode());
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            productList.getChildren().remove(cell.getNode());
            cells.remove(cell);
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void clearAll() {
        productList.getChildren().clear();
        cells.clear();
        stockInRequestList.clear();
        totalPriceLabel.setText("0.0$");
    }

    private void cancel() {
        batchCodeTextField.clear();
        supplierTextField.clear();
        batchNameTextField.clear();
        descriptionTextArea.clear();
        batchCreateDate.clear();
        upperPane.setDisable(true);
        lowerPane.setDisable(true);
        addNewRequest.setDisable(false);
        clearAll();
        resetValidationMessages();
    }

    private void resetValidationMessages() {
        if (batchCodeMessage != null) {
            batchCodeMessage.setText("");
        };
        if (batchNameMessage != null){
            batchNameMessage.setText("");
        }
        batchCodeTextField.getStyleClass().remove("error");
        batchNameTextField.getStyleClass().remove("error");
    }

    // --- Logic for UI ---
    public void getTotalPrice() {
        totalPrice = 0.0;
        for (StockInRequest stockInRequest : stockInRequestList) {
            totalPrice += stockInRequest.getQuantity() * stockInRequest.getPurchasePrice();
        }
        String formattedTotalPrice = String.format("%.3f", totalPrice);
        totalPriceLabel.setText(formattedTotalPrice + "$");
    }

    private void importStockIn() throws IOException {
        BatchInfoDTO batchInfoDTO = getBatchInfo();
        if (batchInfoDTO == null) {
            ErrorLogger.showAlert("Invalid batch information", Alert.AlertType.ERROR);
            return;
        }
        if (checkValidate()) {
            getTotalPrice();
            batchInfoDTO.setTotalPrice(totalPrice);
            BatchInfoServiceImpl.getInstance().add(batchInfoDTO);
            for(StockInRequest stockInRequest : stockInRequestList) {
                String officialImgUrl = FileServiceImpl.getInstance().uploadImage(stockInRequest.getImageUrl());
                stockInRequest.setImageUrl(officialImgUrl);
            }
            StockInServiceImpl.getInstance().stockIn(batchInfoDTO, stockInRequestList);
            cancel();
        }
    }

    private boolean checkValidate() {
        stockInRequestList.clear();

        for (ProductCellController cell : cells) {
            resetCellStyleClass(cell);

            if (cell.getData() == null) {
                ErrorLogger.showAlert("Invalid input", Alert.AlertType.ERROR);
                cell.getNode().getStyleClass().add("error");
                stockInRequestList.clear();
                return false;
            }

            if (stockInRequestList.contains(cell.getData())) {
                ErrorLogger.showAlert("Duplicate product ID", Alert.AlertType.WARNING);
                cell.getNode().getStyleClass().add("warning");
                stockInRequestList.clear();
                return false;
            }

            stockInRequestList.add(cell.getData());
        }

        return true;
    }

    private BatchInfoDTO getBatchInfo() {
        if (!validateBatchCode() || !validateBatchName()) {
            return null;
        };

        if (BatchInfoServiceImpl.getInstance().getById(Integer.parseInt(batchCodeTextField.getText())) != null) {
            ErrorLogger.showAlert("Batch already exists", Alert.AlertType.WARNING);
            return null;
        }

        BatchInfoDTO batchInfoDTO = new BatchInfoDTO();
        batchInfoDTO.setBatchId(Integer.parseInt(batchCodeTextField.getText()));
        batchInfoDTO.setSupplier(supplierTextField.getText());
        batchInfoDTO.setBatchName(batchNameTextField.getText());
        batchInfoDTO.setDescription(descriptionTextArea.getText());
        if (batchCreateDate.getValue() == null) {
            batchInfoDTO.setCreateDate(new Timestamp(System.currentTimeMillis()));
        }
        else {
            batchInfoDTO.setCreateDate(Timestamp.valueOf(batchCreateDate.getValue().atStartOfDay()));
        }
        return batchInfoDTO;
    }

    // --- util methods ---
    private void setListener() {
        batchCodeTextField.textProperty().addListener(event -> validateBatchCode());
        batchNameTextField.textProperty().addListener(event -> validateBatchName());
    }

    private boolean validateBatchCode() {
        return isValidIntegerAndNotNull(batchCodeTextField, batchCodeMessage);
    }

    private boolean validateBatchName() {
        return isNotNull(batchNameTextField, batchNameMessage);
    }

    private boolean isValidIntegerAndNotNull(MFXTextField textField, Label message) {
        return InputValidator.isInteger(textField, message)&&InputValidator.isNotNull(textField,message);
    }

    private boolean isNotNull(MFXTextField textField, Label message) {
        return InputValidator.isNotNull(textField, message);
    }

    private void resetCellStyleClass(ProductCellController cell) {
        cell.getNode().getStyleClass().remove("error");
        cell.getNode().getStyleClass().remove("warning");
    }

   /* //--cache--
    private void saveState() {
        CacheManager.getInstance().put("batchCode", batchCodeTextField.getText());
        CacheManager.getInstance().put("supplier", supplierTextField.getText());
        CacheManager.getInstance().put("batchName", batchNameTextField.getText());
        CacheManager.getInstance().put("description", descriptionTextArea.getText());
        CacheManager.getInstance().put("batchDate", batchCreateDate.getValue());
        CacheManager.getInstance().put("productList", FXCollections.observableArrayList(cells));
    }

    private void restoreState() {
        batchCodeTextField.setText((String) CacheManager.getInstance().get("batchCode"));
        supplierTextField.setText((String) CacheManager.getInstance().get("supplier"));
        batchNameTextField.setText((String) CacheManager.getInstance().get("batchName"));
        descriptionTextArea.setText((String) CacheManager.getInstance().get("description"));
        batchCreateDate.setValue((LocalDate) CacheManager.getInstance().get("batchDate"));
       List<ProductCellController> cachedCells =
                (List<ProductCellController>) CacheManager.getInstance().get("productList");

        if (cachedCells != null) {
            productList.getChildren().clear();
            cells.clear();
            for (ProductCellController cell : cachedCells) {
                productList.getChildren().add(cell.getNode());
                cells.add(cell);
            }
        }
    }*/
}
