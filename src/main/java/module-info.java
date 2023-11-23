module com.example.tester {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.tester to javafx.fxml;
    opens com.example.tester.models to javafx.fxml;
    opens com.example.tester.classes to javafx.base;

    exports com.example.tester;
    exports com.example.tester.controllers;
    opens com.example.tester.controllers to javafx.fxml;
}