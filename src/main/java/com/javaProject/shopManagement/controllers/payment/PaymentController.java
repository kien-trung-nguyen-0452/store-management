package com.javaProject.shopManagement.controllers.payment;

import com.javaProject.shopManagement.dto.ProductDTO;
import com.javaProject.shopManagement.util.effectHandler.EffectHandler;
import com.javaProject.shopManagement.util.effectHandler.EffectType;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.HashSet;

public class PaymentController{
    @FXML
    private Button addNew;
    @FXML
    private MFXScrollPane scrollPane;
    @FXML
    private VBox billList;
    @FXML
    private Label totalAmountLabel;
    @FXML
    private TextField searchBar;
    @FXML
    private Button searchBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button payBtn;

    private ObservableList<BillCardController> cells;
    private double totalAmount;

    @FXML
    private void initialize(){
        addNew.setOnAction(event ->addNew());
        HashSet<ProductDTO> billCardList = new HashSet<>();

    }

    private void addNew(){
        billList.getChildren().add(new BillCardController(new ProductDTO(), this).getNode());

    }

    public void deleteRow(BillCardController cell){
        EffectHandler.getEffect(EffectType.FADE_OUT, cell.getNode());
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            billList.getChildren().remove(cell.getNode());
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }



}
