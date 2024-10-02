module com.javaProject.shopManagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jdk.jdi;

    opens com.javaProject.shopManagement to javafx.fxml;
    exports com.javaProject.shopManagement.controllers;
    exports com.javaProject.shopManagement;
    opens com.javaProject.shopManagement.controllers to javafx.fxml;

}