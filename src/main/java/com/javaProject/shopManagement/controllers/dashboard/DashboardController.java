package com.javaProject.shopManagement.controllers.dashboard;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.util.concurrent.CompletableFuture;

public class DashboardController {

    @FXML
    private AnchorPane dailyLoadingPane;
    @FXML
    private AnchorPane dailyStatisticPane;
    @FXML
    private void initialize(){
        loadDailyStatistics();

    }

    private void loadDailyStatistics(){
        CompletableFuture.runAsync(()->{
         dailyLoadingPane.setVisible(true);

        }).thenAcceptAsync(loadPane ->{
            Platform.runLater(()->{
                Node dailyStatisticContent = new DailyStatisticController().getNode();
                dailyStatisticPane.getChildren().add(dailyStatisticContent);
                dailyLoadingPane.setVisible(false);
            });

        });
    }



}
