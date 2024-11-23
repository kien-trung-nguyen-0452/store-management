package com.javaProject.shopManagement.controllers.payment;

import com.javaProject.shopManagement.controllers.history.BillController;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class PaymentController{
    @FXML
    private Button addNew;

    @FXML
    private MFXScrollPane scrollPane;

    @FXML
    private VBox billList;

    @FXML
    private void initialize(){
        addNew.setOnAction(event ->addNew());
    }

    private void addNew(){
        billList.getChildren().add(new BillCardController().getNode());

    }


}

