package com.javaProject.shopManagement.controllers.history;
import com.javaProject.shopManagement.dto.ProductDTO;
import com.javaProject.shopManagement.dto.SalesDTO;
import com.javaProject.shopManagement.dto.InvoiceDTO;
import com.javaProject.shopManagement.services.implementation.ProductServiceImpl;
import com.javaProject.shopManagement.services.implementation.SalesServiceImpl;
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

public class InvoiceDetailsCardController {
    // -- general
    @FXML private AnchorPane root;
    @FXML private MFXButton cancelBtn;
    @FXML private MFXButton updateBtn;

    // -- accordion
    @FXML private MFXTextField invoiceCodeTextField;
    @FXML private MFXTextField invoiceTotalPriceTextField;
    @FXML private MFXTextField invoiceDateTextField;


    //-- Invoice list
    @FXML private TableView<SalesDTO> tableView;
    @FXML private TableColumn<SalesDTO, String> productIdCol;
    @FXML private TableColumn<SalesDTO, String> batchIdCol;
    @FXML private TableColumn<SalesDTO, String> productNameCol;
    @FXML private TableColumn<SalesDTO, String> quantityCol;
    @FXML private TableColumn<SalesDTO, String> totalPriceCol;
    @FXML private TableColumn<SalesDTO, String> unitPriceCol;

    //--local state
    InvoiceDTO invoiceDTO;
    ObservableList<SalesDTO> invoiceList;
    InvoiceTabController parentController;


    //--constructor
    public InvoiceDetailsCardController(InvoiceDTO invoiceDTO, InvoiceTabController parentController) {
        this.parentController = parentController;
        this.invoiceDTO = invoiceDTO;


        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(InvoiceDetailsCardController.class.getResource("/com/javaProject/shopManagement/public/views/invoice_details.fxml"));
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    //--initialize
    @FXML
    private void initialize() {
        setUpInvoiceGeneralInformation();
        setInvoiceList();
        setTableView();
        setBtn();
    }

    //--general set up
    private void setUpInvoiceGeneralInformation(){
        invoiceCodeTextField.setText(String.valueOf(invoiceDTO.getInvoiceId()));
        invoiceTotalPriceTextField.setText(String.valueOf(invoiceDTO.getTotalAmount()));
        invoiceDateTextField.setText(String.valueOf(invoiceDTO.getDate()));
    }

    private void setBtn(){
        cancelBtn.setOnAction(e->setCancelBtn());

    }

    private void setCancelBtn(){
        parentController.closeDetailsCard();

    }

    //--List views
    private void setInvoiceList(){
        invoiceList = FXCollections.observableArrayList();
        List<SalesDTO> list = SalesServiceImpl.getInstance().getSalesDetails(invoiceDTO.getInvoiceId());
        invoiceList.addAll(list);
    }
    private void setTableView(){
        productIdCol.setCellValueFactory(salesDTO ->new SimpleStringProperty(String.valueOf (salesDTO.getValue().getProductId())));
        batchIdCol.setCellValueFactory(salesDTO -> new SimpleStringProperty(String.valueOf(salesDTO.getValue().getBatchId())));
        productNameCol.setCellValueFactory(salesDTO -> new SimpleStringProperty(String.valueOf(salesDTO.getValue().getProductName())));
        quantityCol.setCellValueFactory(salesDTO -> new SimpleStringProperty(String.valueOf(salesDTO.getValue().getQuantity())));
        totalPriceCol.setCellValueFactory(salesDTO -> new SimpleStringProperty(String.valueOf(salesDTO.getValue().getTotalAmount())));
        unitPriceCol.setCellValueFactory(salesDTO -> new SimpleStringProperty(String.valueOf(salesDTO.getValue().getPrice())));
        tableView.setItems(invoiceList);
    }

    public Node getNode(){
        return root;
    }



}
