module com.example.courseproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jdk.jdi;

    opens com.example.courseproject to javafx.fxml;
    exports com.example.courseproject.controllers;
    exports com.example.courseproject;
    opens com.example.courseproject.controllers to javafx.fxml;

}