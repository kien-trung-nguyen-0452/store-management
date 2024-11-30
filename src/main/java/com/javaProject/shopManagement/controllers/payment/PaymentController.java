package com.javaProject.shopManagement.controllers.payment;

import com.javaProject.shopManagement.dto.InvoiceDTO;
import com.javaProject.shopManagement.dto.ProductDTO;
import com.javaProject.shopManagement.dto.SalesDTO;
import com.javaProject.shopManagement.services.implementation.PaymentServiceImpl;
import com.javaProject.shopManagement.services.implementation.SearchServiceImpl;
import com.javaProject.shopManagement.util.effectHandler.EffectHandler;
import com.javaProject.shopManagement.util.effectHandler.EffectType;
import com.javaProject.shopManagement.util.logger.ErrorLogger;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PaymentController{
    @FXML
    private Label productNotFoundLabel;
    @FXML
    private AnchorPane root;
    @FXML
    private MFXScrollPane searchResultList;
    @FXML
    private VBox searchResultListContent;
    @FXML
    private MFXButton searchButton;
    @FXML
    private MFXScrollPane scrollPane;
    @FXML
    private VBox billList;
    @FXML
    private Label totalAmountLabel;
    @FXML
    private TextField searchBar;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button payBtn;

    private List<SalesDTO> paymentRequest;
    private HashSet<BillCardController> cart;

    private double totalAmount;

    @FXML
    private void initialize(){
        totalAmount = 0.0;
        cart = new HashSet<>();

        root.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if(searchResultList.isHover()){
                searchResultList.setVisible(false);
                productNotFoundLabel.setVisible(false);
            }
        });

        searchButton.setOnAction(actionEvent -> {
            search(searchBar.getText());
        });

        payBtn.setOnAction(actionEvent -> pay());
        cancelBtn.setOnAction(actionEvent -> cancel());
    }


    public void deleteRow(BillCardController cell){
        EffectHandler.getEffect(EffectType.FADE_OUT, cell.getNode());
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            billList.getChildren().remove(cell.getNode());
            cart.remove(cell);
            updateTotalAmount();
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void search(String keyword){
        searchResultListContent.getChildren().clear();
        List<ProductDTO> searchResult = SearchServiceImpl.getInstance().searchProduct(keyword);
        searchResultList.setVisible(true);
        EffectHandler.getEffect(EffectType.SCROLL_DOWN, searchResultList);
        searchResultListContent.setVisible(true);
        if(searchResult.isEmpty()){
            System.out.println("Empty search result");
            productNotFoundLabel.setVisible(true);

        }else {
            productNotFoundLabel.setVisible(false);
            for (ProductDTO product : searchResult) {
                Node node = new SearchResultCellController(product).getNode();
                searchResultListContent.getChildren().add(node);
                node.setOnMouseClicked(event -> addToCart(product));
            }
        }
    }

    private void addToCart(ProductDTO product){
        for (BillCardController controller : cart) {
            if (controller.getProductId() == product.getProductId() && controller.getBatchId() == product.getBatchId()) {
                controller.increaseQuantity();
                updateTotalAmount();
                return;
            }
        }

        BillCardController newBillCard = new BillCardController(product, this);
        cart.add(newBillCard);
        billList.getChildren().add(newBillCard.getNode());
        updateTotalAmount();
    }

    public void updateTotalAmount() {
        totalAmount = 0.0;
        for (BillCardController controller : cart) {
            totalAmount += controller.getTotalPrice();
        }
        totalAmountLabel.setText(String.format("%.2f", totalAmount)+ "$");
    }

    private void pay(){
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setDate(new Timestamp(System.currentTimeMillis()));
        invoiceDTO.setTotalAmount(totalAmount);

        List<SalesDTO> salesDTOList = new ArrayList<>();
        for(BillCardController controller : cart){
            salesDTOList.add(controller.getInvoiceDetails());
        }
        PaymentServiceImpl.getInstance().pay(invoiceDTO,salesDTOList);
        ErrorLogger.showAlert("Pay success", Alert.AlertType.INFORMATION);
        clear();
    }
    private void cancel(){
        if (ErrorLogger.getChoiceBox("Cancel", "Are you sure to cancel payment request?")){
           clear();
        };

    }

    private void clear(){
        cart.clear();
        billList.getChildren().clear();
        searchBar.clear();
    }

}
