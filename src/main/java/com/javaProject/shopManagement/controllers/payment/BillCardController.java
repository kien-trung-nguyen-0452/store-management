package com.javaProject.shopManagement.controllers.payment;

import com.javaProject.shopManagement.exception.GlobalExceptionHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class BillCardController {
    @FXML
    private HBox root;
    public BillCardController(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/javaProject/shopManagement/public/views/bill-card.fxml"));
        loader.setController(this);
        try {
            root = loader.load();
        } catch (Exception e) {
            GlobalExceptionHandler.handleException(e);
        }
    }

    public Node getNode() {
        return root;
    }
}
