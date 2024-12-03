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

}
