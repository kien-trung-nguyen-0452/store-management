module com.javaProject.shopManagement {

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jdk.jdi;
    requires de.jensd.fx.glyphs.fontawesome;
    requires com.jfoenix;
    requires MaterialFX;
    requires com.google.protobuf;

    opens com.javaProject.shopManagement.controllers.layout to javafx.fxml;
    exports com.javaProject.shopManagement;
    exports com.javaProject.shopManagement.controllers.layout;

    exports com.javaProject.shopManagement.entity;
    exports com.javaProject.shopManagement.dto;
    exports com.javaProject.shopManagement.services.implementation;
    exports com.javaProject.shopManagement.services.interfaces;
    exports com.javaProject.shopManagement.controllers.stockIn;
    opens com.javaProject.shopManagement.controllers.stockIn to javafx.fxml;
    exports com.javaProject.shopManagement.controllers.warehouse;
    opens com.javaProject.shopManagement.controllers.warehouse to javafx.fxml;
    exports com.javaProject.shopManagement.controllers.payment;
    opens com.javaProject.shopManagement.controllers.payment to javafx.fxml;
    exports com.javaProject.shopManagement.controllers.dashboard;
    opens com.javaProject.shopManagement.controllers.dashboard to javafx.fxml;
    exports com.javaProject.shopManagement.controllers.history;
    opens com.javaProject.shopManagement.controllers.history to javafx.fxml;
    exports com.javaProject.shopManagement.dto.product;
    opens com.javaProject.shopManagement.dto.product to javafx.fxml;

}