package com.javaProject.shopManagement.controllers.dashboard;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.util.concurrent.CompletableFuture;

public class DashboardController {

    @FXML
    private AnchorPane generalLoadingPane;
    @FXML
    private AnchorPane dailyLoadingPane;
    @FXML
    private AnchorPane dailyStatisticPane;
    @FXML
    private AnchorPane generalStatisticPane;
    @FXML
    private void initialize(){
        loadDailyStatistics();
        loadingGeneralStatistics();

    }

    private void loadDailyStatistics(){
        CompletableFuture.runAsync(()->{
         dailyLoadingPane.setVisible(true);

        }).thenAcceptAsync(loadPane ->{
            Node dailyStatisticContent = new DailyStatisticController().getNode();
            Platform.runLater(()->{
                setAnchor(dailyStatisticContent, 0,0,0,0);
                dailyStatisticPane.getChildren().add(dailyStatisticContent);
                dailyLoadingPane.setVisible(false);
            });

        });
    }
    private void loadingGeneralStatistics(){
        CompletableFuture.runAsync(()->{
            generalStatisticPane.setVisible(true);
        }).thenAcceptAsync(loadPane ->{
            Node generalStatisticContent = new GeneralStatisticController().getNode();
            Platform.runLater(()->{
                setAnchor(generalStatisticContent,0,0,0,0);
                generalStatisticPane.getChildren().add(generalStatisticContent);
                generalLoadingPane.setVisible(false);
            });
        });
    }

    private void setAnchor(Node node, double top, double left, double right, double bottom){
        AnchorPane.setTopAnchor(node, top);
        AnchorPane.setLeftAnchor(node, left);
        AnchorPane.setRightAnchor(node, right);
        AnchorPane.setBottomAnchor(node, bottom);

    }



}
