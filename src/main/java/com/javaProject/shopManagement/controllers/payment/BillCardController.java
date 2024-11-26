package com.javaProject.shopManagement.controllers.payment;

import com.javaProject.shopManagement.dto.ProductDTO;
import com.javaProject.shopManagement.dto.SalesDTO;
import com.javaProject.shopManagement.exception.GlobalExceptionHandler;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class BillCardController {
    @FXML
    private MFXTextField productId;
    @FXML
    private MFXTextField productName;
    @FXML
    private ImageView imageView;
    @FXML
    private MFXButton increaseQuantity;
    @FXML
    private MFXTextField productQuantity;
    @FXML
    private MFXButton decreaseQuantity;
    @FXML
    private MFXTextField remainQuantity;
    @FXML
    private MFXTextField productPrice;
    @FXML
    private MFXTextField totalPrice;
    @FXML
    private MFXButton deleteCellBtn;
    @FXML
    private HBox root;

    private final ProductDTO productDTO;

    private int currentQuantity =1;
    private final int maxQuantity;

    private PaymentController paymentController;

    public BillCardController(ProductDTO productDTO, PaymentController paymentController) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/javaProject/shopManagement/public/views/bill-card.fxml"));
        loader.setController(this);
        try {
            root = loader.load();
        } catch (Exception e) {
            GlobalExceptionHandler.handleException(e);
        }

        this.paymentController = paymentController;
        this.productDTO = productDTO;
        maxQuantity = productDTO.getQuantity();

    }
    @FXML
    private void initialize() {
        productId.setText(String.valueOf(productDTO.getProductId())) ;
        productName.setText(productDTO.getProductName());
        remainQuantity.setText(String.valueOf(productDTO.getQuantity()));
        productQuantity.textProperty().addListener((observable, oldValue, newValue) -> {
            try{
                 int value = Integer.parseInt(newValue);
                if (value > maxQuantity||value <0){
                    productQuantity.setText("1");
                }
            }catch (Exception e){
                productQuantity.setText("1");
            }
        });
        productPrice.setText(String.valueOf(productDTO.getSellingPrice()));
        imageView.setImage(new Image("file:" + productDTO.getImageUrl()));
        updateTotalPrice();

        increaseQuantity.setOnAction( e ->
        {
            increaseQuantity();
            if (currentQuantity==maxQuantity){
                increaseQuantity.setDisable(true);
            }});

        decreaseQuantity.setOnAction( e -> decreaseQuantity());
        deleteCellBtn.setOnAction(e -> deleteCellBtn(paymentController));
    }

    private void deleteCellBtn(PaymentController paymentController) {
        paymentController.deleteRow(this);
    }

    public void increaseQuantity() {
        if (currentQuantity < maxQuantity) {
            currentQuantity++;
        }
    }
    public void decreaseQuantity() {
        if(currentQuantity > 0){
            currentQuantity--;
        }
    }

    private double getTotalPrice() {
       return productDTO.getSellingPrice() * currentQuantity;
    }





    public void updateTotalPrice(){
        int quantity = Integer.parseInt(productQuantity.getText());
        double price = Double.parseDouble(productPrice.getText());
        double total = quantity * price;
        totalPrice.setText(String.format("%.2f", total));
    }

    public SalesDTO getInvoiceDetails() {
        SalesDTO salesDTO = new SalesDTO();
        salesDTO.setBatchId(productDTO.getBatchId());
        salesDTO.setProductId(productDTO.getProductId());
        salesDTO.setProductName(productDTO.getProductName());
        salesDTO.setQuantity(currentQuantity);
        salesDTO.setPrice(getTotalPrice());

        return salesDTO;


    }

    public Node getNode() {
        return root;
    }
}
