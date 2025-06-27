module com.chris.venta_de_boletos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    exports com.chris.venta_de_boletos.Controllers;
    opens com.chris.venta_de_boletos.Controllers to javafx.fxml;

    opens com.chris.venta_de_boletos to javafx.fxml;
    exports com.chris.venta_de_boletos;
    exports com.chris.venta_de_boletos.ConexionBD;
    opens com.chris.venta_de_boletos.ConexionBD to javafx.fxml;
}