package com.javaProject.shopManagement.controllers.history;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.util.concurrent.CompletableFuture;

public class BillController  {
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab invoiceTab;
    @FXML
    private Tab stockInTab;

    @FXML
    private AnchorPane loadingPane;

    @FXML
    private void initialize() {
        CompletableFuture.runAsync(()->{
            loadingPane.setVisible(true);
        }).thenAcceptAsync(loadUI->{
            Node invoiceTabContent = new InvoiceTabController().getNode();
            Node stockInTabContent = new StockInTabController().getNode();
            Platform.runLater(()->{
                invoiceTab.setContent(invoiceTabContent);
                stockInTab.setContent(stockInTabContent);
                loadingPane.setVisible(false);

            });
        });
    }


    /*@FXML
    MFXPaginatedTableView<InvoiceDTO> tableView;
    ObservableList<InvoiceDTO> data;

    @FXML
    private void initialize() {
        data = FXCollections.observableArrayList();
        List<InvoiceDTO> invoiceDTOS = InvoiceServiceImpl.getInstance().getAllInvoices();
        data.addAll(invoiceDTOS);
        setTableView();


    }


    private void setTableView(){
        MFXTableColumn<InvoiceDTO> invoiceCodeCol = new MFXTableColumn<>("Invoice Code", true, Comparator.comparing(InvoiceDTO::getInvoiceId));
        MFXTableColumn<InvoiceDTO> invoiceDateCol = new MFXTableColumn<>("Date", true, Comparator.comparing(InvoiceDTO::getDate));
        MFXTableColumn<InvoiceDTO> invoiceTotalCol = new MFXTableColumn<>("Total Amount", true, Comparator.comparing(InvoiceDTO::getTotalAmount));

        invoiceCodeCol.setRowCellFactory(invoiceDTO -> new MFXTableRowCell<>(InvoiceDTO::getInvoiceId));
        invoiceDateCol.setRowCellFactory(invoiceDTO -> new MFXTableRowCell<>(InvoiceDTO::getDate));
        invoiceTotalCol.setRowCellFactory(invoiceDTO -> new MFXTableRowCell<>(InvoiceDTO::getTotalAmount));

        tableView.getTableColumns().addAll(invoiceCodeCol, invoiceDateCol, invoiceTotalCol);
        tableView.setItems(data);

    }
*/
}
