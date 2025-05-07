module com.nikitos.homeflow {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.nikitos.homeflow to javafx.fxml;
    exports com.nikitos.homeflow;
    exports com.nikitos.homeflow.Controller;
    opens com.nikitos.homeflow.Controller to javafx.fxml;
    exports com.nikitos.homeflow.Controller.AddControllers;
    opens com.nikitos.homeflow.Controller.AddControllers to javafx.fxml;
}