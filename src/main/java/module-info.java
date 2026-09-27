module com.example.coffeeshop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;

    opens com.example.coffeeshop to javafx.fxml;
    exports com.example.coffeeshop;
}