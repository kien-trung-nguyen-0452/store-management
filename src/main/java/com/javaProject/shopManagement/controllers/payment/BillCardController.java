package com.javaProject.shopManagement.controllers.payment;

import com.javaProject.shopManagement.dto.product.ProductDTO;
import com.javaProject.shopManagement.dto.SalesDTO;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class BillCardController {

    // FXML Fields
    @FXML private MFXTextField productId;
    @FXML private MFXTextField productName;
    @FXML private ImageView productImage;
    @FXML private MFXButton increaseQuantityBtn;
    @FXML private MFXTextField productQuantity;
    @FXML private MFXButton decreaseQuantityBtn;
    @FXML private MFXTextField remainQuantity;
    @FXML private MFXTextField productPrice;
    @FXML private MFXTextField totalPrice;
    @FXML private MFXButton deleteCellBtn;
    @FXML private HBox root;

    // Private fields
    private final ProductDTO productDTO;
    private final PaymentController parentController;
    private final int maxQuantity;
    private int currentQuantity = 1;
    private double productTotalPrice;

    // Constructor
    public BillCardController(ProductDTO productDTO, PaymentController parentController) {
        this.productDTO = productDTO;
        this.parentController = parentController;
        this.maxQuantity = productDTO.getQuantity();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/javaProject/shopManagement/public/views/bill-card.fxml"));
        loader.setController(this);
        try {
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Initialize method
    @FXML
    private void initialize() {
        initializeFields();
        initializeListeners();
        initializeButtons();
    }

    // Initialization helpers
    private void initializeFields() {
        productId.setText(String.valueOf(productDTO.getProductId()));
        productName.setText(productDTO.getProductName());
        remainQuantity.setText(String.valueOf(maxQuantity));
        productQuantity.setText(String.valueOf(currentQuantity));
        productPrice.setText(String.valueOf(productDTO.getSellingPrice()));
        productImage.setImage(new Image("file:" + productDTO.getImageUrl()));
        updateTotalPrice();
    }

    private void initializeListeners() {
        productQuantity.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int value = Integer.parseInt(newValue);
                if (value > maxQuantity) {
                    currentQuantity = maxQuantity;
                } else if (value <= 0) {
                    currentQuantity = 1;
                } else {
                    currentQuantity = value;
                }
            } catch (Exception e) {
                currentQuantity = 1;
            }

            javafx.application.Platform.runLater(() -> {
                productQuantity.setText(String.valueOf(currentQuantity));
                decreaseQuantityBtn.setDisable(currentQuantity == 1);
                increaseQuantityBtn.setDisable(currentQuantity == maxQuantity);
                setTotalPrice();
                updateTotalPrice();
                parentController.updateTotalAmount();

            });
        });
    }

    private void initializeButtons() {
        increaseQuantityBtn.setOnAction(e -> {
            increaseQuantity();
            updateTotalPrice();
        });

        decreaseQuantityBtn.setOnAction(e -> {
            decreaseQuantity();
            updateTotalPrice();
        });

        deleteCellBtn.setOnAction(e -> deleteCell());
    }

    // Button actions
    private void deleteCell() {
        parentController.deleteRow(this);
    }

    public void increaseQuantity() {
        if (currentQuantity < maxQuantity) {
            currentQuantity++;
            parentController.updateTotalAmount();
            productQuantity.setText(String.valueOf(currentQuantity));
        }
    }

    public void decreaseQuantity() {
        if (currentQuantity > 1) {
            currentQuantity--;
            productQuantity.setText(String.valueOf(currentQuantity));
            parentController.updateTotalAmount();
        }
    }

    // Calculation methods
    private void setTotalPrice() {
       productTotalPrice = productDTO.getSellingPrice() * currentQuantity;
    }

    public void updateTotalPrice() {
         setTotalPrice();
        totalPrice.setText(String.format("%.2f", productTotalPrice));
    }

    // Data export methods
    public SalesDTO getInvoiceDetails() {
        SalesDTO salesDTO = new SalesDTO();
        salesDTO.setBatchId(productDTO.getBatchId());
        salesDTO.setProductId(productDTO.getProductId());
        salesDTO.setProductName(productDTO.getProductName());
        salesDTO.setQuantity(currentQuantity);
        salesDTO.setPrice(productDTO.getSellingPrice());
        salesDTO.setTotalAmount(productTotalPrice);
        return salesDTO;
    }

    // Getters
    public Node getNode() {
        return root;
    }

    public int getProductId(){
        return productDTO.getProductId();
    }
    public int getBatchId(){return productDTO.getBatchId();
    }

    public double getTotalPrice(){
        return productTotalPrice;
    }
}
