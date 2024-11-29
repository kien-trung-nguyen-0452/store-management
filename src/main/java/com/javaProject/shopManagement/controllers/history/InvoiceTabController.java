package com.javaProject.shopManagement.controllers.history;

import com.javaProject.shopManagement.dto.InvoiceDTO;
import com.javaProject.shopManagement.services.implementation.InvoiceServiceImpl;
import com.javaProject.shopManagement.util.effectHandler.EffectHandler;
import com.javaProject.shopManagement.util.effectHandler.EffectType;
import com.javaProject.shopManagement.util.motion.DraggableNode;
import com.javaProject.shopManagement.util.motion.PopUpEffect;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPagination;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.util.List;

public class InvoiceTabController {

    //--General FXML
    @FXML private TextField searchBar;
    @FXML private MFXButton searchButton;
    @FXML private AnchorPane tabPane;
    @FXML private MFXButton openFilterBtn;
    @FXML private TableView<InvoiceDTO> tableView;
    @FXML private TableColumn<InvoiceDTO, String> invoiceCodeCol;
    @FXML private TableColumn<InvoiceDTO, String> invoiceDateCol;
    @FXML private TableColumn<InvoiceDTO, String> invoiceTotalAmountCol;
    @FXML private MFXPagination pagination;

    //--Filter pane FXML
    @FXML private BorderPane filterPane;
    @FXML private MFXDatePicker startDatePicker;
    @FXML private MFXDatePicker endDatePicker;
    @FXML private MFXButton filterBtn;
    @FXML private MFXButton resetFilterPaneBtn;
    @FXML private MFXButton closeFilterPaneBtn;

    //--Local state
    ObservableList<InvoiceDTO> data;
    private double x;
    private double y;

    public InvoiceTabController(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/javaProject/shopManagement/public/views/invoice_tab.fxml"));
            fxmlLoader.setController(this);
            tabPane = fxmlLoader.load();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        data = FXCollections.observableArrayList();
        reFresh();
        setTableColumns();
        setTableItems();
        setFilterPane();
    }

    public void filterInvoice(){


    }

    private void reFresh(){
        List<InvoiceDTO> invoiceDTOS = InvoiceServiceImpl.getInstance().getAllInvoices();
        data.addAll(invoiceDTOS);

    }

    private void setTableItems(){
        tableView.setItems(data);
    }

    private void setTableColumns(){
        invoiceCodeCol.setCellValueFactory(invoiceDTO ->new SimpleStringProperty(String.valueOf(invoiceDTO.getValue().getInvoiceId())));
        invoiceDateCol.setCellValueFactory(invoiceDTO ->new SimpleStringProperty(String.valueOf(invoiceDTO.getValue().getDate())));
        invoiceTotalAmountCol.setCellValueFactory(invoiceDTO ->new SimpleStringProperty(String.valueOf(invoiceDTO.getValue().getTotalAmount())));
        setTableItems();

    }
    private void setFilterPane(){
        DraggableNode.setDraggable(filterPane);
        openFilterBtn.setOnAction(e->openFilterPane());
        resetFilterPaneBtn.setOnAction(e->resetFilterPane());
        closeFilterPaneBtn.setOnAction(e->{
            resetFilterPane();
            closeFilterPane();});

    }
    private void openFilterPane(){
        EffectHandler.setShowUP(filterPane, filterPane.isVisible());
    }
    private void closeFilterPane(){
        filterPane.setVisible(false);
    }
    private void resetFilterPane(){
        startDatePicker.clear();
        endDatePicker.clear();
    }


    public Node getNode() {
        return tabPane;
    }



}
