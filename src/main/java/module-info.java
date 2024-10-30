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

    opens com.javaProject.shopManagement.controllers.layout to javafx.fxml;
    exports com.javaProject.shopManagement.controllers.layout;
    exports com.javaProject.shopManagement;
    exports com.javaProject.shopManagement.entity;
    exports com.javaProject.shopManagement.dto;

}