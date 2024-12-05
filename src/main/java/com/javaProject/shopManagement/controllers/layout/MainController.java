package com.javaProject.shopManagement.controllers.layout;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;
import java.io.IOException;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainController implements Initializable {

  @FXML
  private Button exitButton;

  @FXML
  private ToggleButton toggleButton;

  @FXML
  private Button hideButton;

  @FXML
  private StackPane contentArea;
  private final Map<String, Parent> cache = new HashMap<>();


    @Override
  public void initialize(URL location, ResourceBundle resourceBundle){

      try  {
          Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/javaProject/shopManagement/public/views/payment.fxml")));
          contentArea.getChildren().removeAll();
          contentArea.getChildren().setAll(fxml);

      } catch (IOException e) {
          Logger.getLogger(ModuleLayer.Controller.class.getName()).log(Level.SEVERE, null, e);
      }


  }

  public void payment(javafx.event.ActionEvent actionEvent) throws IOException {
        String pageKey = "payment";
        Parent fxml;
      if(cache.containsKey(pageKey)){
          fxml = cache.get(pageKey);
      }else {
         fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/javaProject/shopManagement/public/views/payment.fxml")));
         cache.put(pageKey, fxml);
      }
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
  }

  public void bill(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/javaProject/shopManagement/public/views/bill.fxml")));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
  }

  public void warehouse(javafx.event.ActionEvent actionEvent) throws IOException {

          Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/javaProject/shopManagement/public/views/warehouse.fxml")));
          contentArea.getChildren().removeAll();
          contentArea.getChildren().setAll(fxml);

  }
  public void dashboard(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/javaProject/shopManagement/public/views/dashboard.fxml")));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
  }

  public void stockIn(javafx.event.ActionEvent actionEvent) throws IOException {

        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/javaProject/shopManagement/public/views/stockIn.fxml")));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }



}