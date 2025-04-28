module com.chris.venta_de_boletos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.chris.venta_de_boletos to javafx.fxml;
    exports com.chris.venta_de_boletos;
}