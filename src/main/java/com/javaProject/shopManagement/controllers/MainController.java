package com.javaProject.shopManagement.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;
import java.io.IOException;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
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

  @Override
  public void initialize(URL location, ResourceBundle resourceBundle){

      exitButton.setOnAction(event -> System.exit(0));

      toggleButton.setOnAction(event -> {
          Stage stage = (Stage) toggleButton.getScene().getWindow();
          if (toggleButton.isSelected()) {
              stage.setMaximized(true);
          } else {
              stage.setMaximized(false);
              stage.setIconified(false); //
          }

      });

      hideButton.setOnAction(event -> {
          Stage stage = (Stage) hideButton.getScene().getWindow();
          stage.setIconified(true);
      });

      try  {
          Parent fxml = FXMLLoader.load(getClass().getResource("/com/javaProject/shopManagement/public/views/payment.fxml"));
          contentArea.getChildren().removeAll();
          contentArea.getChildren().setAll(fxml);

      } catch (IOException e) {
          Logger.getLogger(ModuleLayer.Controller.class.getName()).log(Level.SEVERE, null, e);
      }


  }

  public void payment(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/javaProject/shopManagement/public/views/payment.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
  }

  public void bill(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/javaProject/shopManagement/public/views/bill.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
  }

  public void warehouse(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/javaProject/shopManagement/public/views/warehouse.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
  }
  public void dashboard(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/javaProject/shopManagement/public/views/dashboad.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
  }



}