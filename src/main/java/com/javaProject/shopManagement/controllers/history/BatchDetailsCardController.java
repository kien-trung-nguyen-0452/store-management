
package com.javaProject.shopManagement.controllers.history;

import com.javaProject.shopManagement.dto.BatchDTO;
import com.javaProject.shopManagement.dto.BatchInfoDTO;
import com.javaProject.shopManagement.services.implementation.BatchInfoServiceImpl;
import com.javaProject.shopManagement.services.implementation.BatchServiceImpl;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.util.List;

public class BatchDetailsCardController {
    // -- general
    @FXML private AnchorPane root;
    @FXML private MFXButton cancelBtn;
    @FXML private MFXButton updateBtn;

    // -- accordion
    @FXML private MFXTextField batchSupplierTextField;
    @FXML private MFXTextField batchIdTextField;
    @FXML private MFXTextField batchTotalPriceTextField;
    @FXML private MFXTextField batchDescriptionTextField;
    @FXML private MFXTextField bathNameTextField;
    @FXML private MFXTextField batchDateTextField;


    //-- batch list
    @FXML private TableView<BatchDTO> tableView;
    @FXML private TableColumn<BatchDTO, String> idCol;
    @FXML private TableColumn<BatchDTO, String> nameCol;
    @FXML private TableColumn<BatchDTO, String> quantityCol;
    @FXML private TableColumn<BatchDTO, String> priceCol;

    //--local state
    BatchInfoDTO batchInfoDTO;
    ObservableList<BatchDTO> batchList;
    StockInTabController parentController;


    //--constructor
    public BatchDetailsCardController(BatchInfoDTO batchInfoDTO, StockInTabController parentController) {
        this.parentController = parentController;
        this.batchInfoDTO = batchInfoDTO;


        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(BatchDetailsCardController.class.getResource("/com/javaProject/shopManagement/public/views/batch_details.fxml"));
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    //--initialize
    @FXML
    private void initialize() {
        setUpBatchGeneralInformation();
        setBatchList();
        setTableView();
        setBtn();
    }

    //--general set up
    private void setUpBatchGeneralInformation(){
        batchIdTextField.setText(String.valueOf(batchInfoDTO.getBatchId()));
        bathNameTextField.setText(batchInfoDTO.getBatchName());
        batchTotalPriceTextField.setText(String.valueOf(batchInfoDTO.getTotalPrice()));
        batchSupplierTextField.setText(batchInfoDTO.getSupplier());
        batchDescriptionTextField.setText(batchInfoDTO.getDescription());
        batchDateTextField.setText(String.valueOf(batchInfoDTO.getCreateDate()));
    }

    private void setBtn(){
        cancelBtn.setOnAction(e->setCancelBtn());
        updateBtn.setOnAction(e->setUpdateBtn());


    }

    private void setCancelBtn(){
        parentController.closeDetailsCard();

    }
    private void setUpdateBtn() {
        if (!bathNameTextField.getText().isEmpty()){
            batchInfoDTO.setBatchName(bathNameTextField.getText());
        }
        if(!batchDescriptionTextField.getText().isEmpty()){
            batchInfoDTO.setDescription(batchDescriptionTextField.getText());
        }

        BatchInfoServiceImpl.getInstance().update(batchInfoDTO);
    }

    //--List views
    private void setBatchList(){
        batchList = FXCollections.observableArrayList();
        List<BatchDTO> list = BatchServiceImpl.getInstance().getBatchByID(batchInfoDTO.getBatchId());
        batchList.addAll(list);
    }
    private void setTableView(){
        idCol.setCellValueFactory(batchDTO ->new SimpleStringProperty(String.valueOf (batchDTO.getValue().getProductId())));
        nameCol.setCellValueFactory(batchDTO -> new SimpleStringProperty(batchDTO.getValue().getProductName()));
        quantityCol.setCellValueFactory(batchDTO -> new SimpleStringProperty(String.valueOf(batchDTO.getValue().getQuantity())));
        priceCol.setCellValueFactory(batchDTO -> new SimpleStringProperty(String.valueOf(batchDTO.getValue().getPurchasePrice())));
        tableView.setItems(batchList);
    }

    public Node getNode(){
        return root;
    }



}
