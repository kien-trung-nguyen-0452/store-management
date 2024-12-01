package com.javaProject.shopManagement.controllers.history;

import com.javaProject.shopManagement.dto.InvoiceDTO;
import com.javaProject.shopManagement.services.implementation.InvoiceServiceImpl;
import com.javaProject.shopManagement.services.implementation.SearchServiceImpl;
import com.javaProject.shopManagement.util.effectHandler.EffectHandler;
import com.javaProject.shopManagement.util.effectHandler.EffectType;
import com.javaProject.shopManagement.util.motion.DraggableNode;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.sql.Timestamp;
import java.util.List;

public class InvoiceTabController {

    //--General FXML
    @FXML AnchorPane invoiceDetailsCard;
    @FXML private TextField searchBar;
    @FXML private MFXButton searchButton;
    @FXML private AnchorPane tabPane;
    @FXML private MFXButton openFilterBtn;
    @FXML private MFXButton refreshDataBtn;
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
    private final int ROWS_PER_PAGE = 10;

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
        setDetailsCard();
        setGeneralButtons();
    }

    private void filterInvoice(){
        Timestamp start = Timestamp.valueOf(startDatePicker.getValue().atStartOfDay());
        Timestamp end = Timestamp.valueOf(endDatePicker.getValue().atTime(23,59,59));
        List<InvoiceDTO> invoiceDTOS = InvoiceServiceImpl.getInstance().getInvoicesByDateRange(start, end);
        reFresh(invoiceDTOS);


    }

    private void reFresh(){
        List<InvoiceDTO> invoiceDTOS = InvoiceServiceImpl.getInstance().getAllInvoices();
        reFresh(invoiceDTOS);

    }
    private void reFresh(List<InvoiceDTO>  newDatalist){
        closeDetailsCard();
        data.clear();
        data.addAll(newDatalist);
        configurePagination();
    }
    private void reFresh(InvoiceDTO newDatalist){
        closeDetailsCard();
        data.clear();
        if(newDatalist != null){
            data.addAll(newDatalist);
        }
        configurePagination();
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
    //-- filter pane
    private void setFilterPane(){
        DraggableNode draggableNode = new DraggableNode();
        draggableNode.makeNodeDraggable(filterPane);
        openFilterBtn.setOnAction(e->openFilterPane());
        resetFilterPaneBtn.setOnAction(e->resetFilterPane());
        closeFilterPaneBtn.setOnAction(e->{
            resetFilterPane();
            closeFilterPane();});
        filterBtn.setOnAction(e->{
            filterInvoice();
        });

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
    //--search
    private void setGeneralButtons(){
        searchButton.setOnAction(e->searchByInvoiceCode());
        refreshDataBtn.setOnAction(e->reFresh());
    }

    private void searchByInvoiceCode(){
        reFresh(SearchServiceImpl.getInstance().searchInvoiceById(searchBar.getText()));

    }

    //--Details card
    private void setDetailsCard(){
        tableView.setOnMouseClicked(event -> {
            invoiceDetailsCard.getChildren().clear();
            invoiceDetailsCard.setVisible(true);
            InvoiceDTO invoiceDTO = tableView.getSelectionModel().getSelectedItem();
            if(invoiceDTO != null){
                InvoiceDetailsCardController InvoiceDetailsCardController = new InvoiceDetailsCardController(invoiceDTO, this);
                invoiceDetailsCard.getChildren().add(InvoiceDetailsCardController.getNode());
                System.out.println(invoiceDetailsCard.getChildren().isEmpty());
                EffectHandler.getEffect(EffectType.FADE_IN, invoiceDetailsCard);
            }




        });
    }
    public void closeDetailsCard(){
        EffectHandler.getEffect(EffectType.FADE_OUT, invoiceDetailsCard);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), event -> {
            invoiceDetailsCard.getChildren().clear();
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }
    //--config pagination
    private void configurePagination() {
        int totalPages = (int) Math.ceil((double) data.size() / ROWS_PER_PAGE);
        pagination.setMaxPage(totalPages);
        pagination.setCurrentPage(1); // Default to first page

        // Add listener to handle page change
        pagination.currentPageProperty().addListener((observable, oldValue, newValue) -> {
            updateTableView(newValue.intValue());
        });

        // Load the first page
        updateTableView(1);
    }

    private void updateTableView(int page){
        int fromIndex = (page - 1) * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, data.size());
        List<InvoiceDTO> pageData = data.subList(fromIndex, toIndex);
        tableView.setItems(FXCollections.observableArrayList(pageData));
    }

    public Node getNode() {
        return tabPane;
    }



}
