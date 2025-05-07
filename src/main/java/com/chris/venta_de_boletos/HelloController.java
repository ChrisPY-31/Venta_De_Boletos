package com.chris.venta_de_boletos;

import com.chris.venta_de_boletos.Service.GestorBoletos;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.controlsfx.control.spreadsheet.Grid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class HelloController {
    @FXML private ComboBox<String> cbCategorias;
    @FXML private Label lblPrecio;
    @FXML private GridPane gridAsientos;
    @FXML private ListView<String> lvBoletosSeleccionados;
    @FXML private Label lblTotal;

    private GestorBoletos gestorBoletos;
    private double totalCompra = 0;

    public void initialize() {
        gestorBoletos = new GestorBoletos(10, 15); // 10 filas, 15 columnas

        cbCategorias.getItems().addAll(gestorBoletos.getPreciosCategorias().keySet());
        cbCategorias.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                lblPrecio.setText("$" + gestorBoletos.getPreciosCategorias().get(newVal));
                actualizarVistaAsientos(newVal);
            }
        });

        cbCategorias.getSelectionModel().selectFirst();
    }

    private void actualizarVistaAsientos(String categoria) {
        gridAsientos.getChildren().clear();
        boolean[][] asientosOcupados = gestorBoletos.getAsientosOcupados();

        for (int i = 0; i < asientosOcupados.length; i++) {
            for (int j = 0; j < asientosOcupados[i].length; j++) {
                Rectangle asiento = new Rectangle(30, 30);
                asiento.setFill(asientosOcupados[i][j] ? Color.RED : Color.GREEN);

                String asientoStr = String.format("%s-%d", (char)('A' + i), j + 1);
                Tooltip.install(asiento, new Tooltip("Asiento " + asientoStr));

                final int fila = i;
                final int columna = j;
                asiento.setOnMouseClicked(e -> {
                    if (!asientosOcupados[fila][columna]) {
                        String nuevoAsiento = String.format("%s-%d", (char)('A' + fila), columna + 1);
                        double precio = gestorBoletos.getPreciosCategorias().get(categoria);

                        lvBoletosSeleccionados.getItems().add(
                                String.format("%s - Asiento %s - $%.2f", categoria, nuevoAsiento, precio)
                        );

                        totalCompra += precio;
                        lblTotal.setText(String.format("Total: $%.2f", totalCompra));
                    }
                });

                gridAsientos.add(asiento, j, i);
            }
        }
    }

    @FXML
    private void confirmarCompra() {
        if (lvBoletosSeleccionados.getItems().isEmpty()) {
            mostrarAlerta("Error", "Carrito vacío",
                    "No ha seleccionado ningún boleto para comprar.", Alert.AlertType.ERROR);
            return;
        }

        // 2. Confirmación con el usuario
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Compra");
        confirmacion.setHeaderText("¿Desea confirmar la compra?");
        confirmacion.setContentText("Total a pagar: $" + totalCompra);

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isEmpty() || resultado.get() != ButtonType.OK) {
            return; // Usuario canceló la operación
        }

        String categoriaActual = cbCategorias.getSelectionModel().getSelectedItem();
        List<String> asientosComprados = new ArrayList<>();
        List<String> boletosNoDisponibles = new ArrayList<>();
        double totalReal = 0;

        for (String item : lvBoletosSeleccionados.getItems()) {
            String[] partes = item.split(" - ");
            String categoria = partes[0];
            String asiento = partes[1].replace("Asiento ", "");
            double precio = Double.parseDouble(partes[2].replace("$", ""));

            // Verificar disponibilidad real
            if (gestorBoletos.comprarBoleto(categoria, asiento)) {
                asientosComprados.add(asiento);
                totalReal += precio;
            } else {
                boletosNoDisponibles.add(asiento);
            }
        }

        if (!boletosNoDisponibles.isEmpty()) {
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("Los siguientes asientos ya no estaban disponibles:\n");
            boletosNoDisponibles.forEach(a -> mensaje.append("- ").append(a).append("\n"));

            if (!asientosComprados.isEmpty()) {
                mensaje.append("\nSe procesaron correctamente ")
                        .append(asientosComprados.size())
                        .append(" boletos.\nTotal cobrado: $")
                        .append(totalReal);
            }

            mostrarAlerta("Advertencia", "Algunos boletos no disponibles",
                    mensaje.toString(), Alert.AlertType.WARNING);
        } else {
            mostrarAlerta("Éxito", "Compra completada",
                    "Se compraron " + asientosComprados.size() +
                            " boletos correctamente.\nTotal: $" + totalReal,
                    Alert.AlertType.INFORMATION);
        }

        if (!asientosComprados.isEmpty()) {
            String asientosStr = String.join(", ", asientosComprados);
            gestorBoletos.generarReporteVenta(categoriaActual, asientosStr, totalReal);
        }

        lvBoletosSeleccionados.getItems().clear();
        totalCompra = 0;
        lblTotal.setText("Total: $0.00");
        actualizarVistaAsientos(categoriaActual);
    }

    @FXML
    private void generarReporteDia() {
        TextInputDialog dialog = new TextInputDialog(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dialog.setTitle("Reporte Diario");
        dialog.setHeaderText("Generar Reporte de Ventas");
        dialog.setContentText("Ingrese la fecha (dd/mm/aaaa):");

        Optional<String> resultado = dialog.showAndWait();
        if (resultado.isEmpty()) {
            return;
        }

        LocalDate fechaReporte;
        try {
            fechaReporte = LocalDate.parse(resultado.get(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            mostrarAlerta("Error", "Formato inválido",
                    "Por favor ingrese la fecha en formato dd/mm/aaaa", Alert.AlertType.ERROR);
            return;
        }

        String nombreArchivo = "reporte_ventas_" + fechaReporte.format(DateTimeFormatter.ofPattern("ddMMyyyy")) + ".txt";

        try {
            java.nio.file.Path path = java.nio.file.Paths.get(nombreArchivo);
            if (!Files.exists(path)) {
                mostrarAlerta("Información", "Reporte no encontrado",
                        "No hay ventas registradas para la fecha seleccionada.",
                        Alert.AlertType.INFORMATION);
                return;
            }

            String contenido = Files.readString(path);

            Stage stage = new Stage();
            stage.setTitle("Reporte de Ventas - " + fechaReporte.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            TextArea textArea = new TextArea(contenido);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            ScrollPane scrollPane = new ScrollPane(textArea);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);

            Scene scene = new Scene(scrollPane, 600, 400);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            mostrarAlerta("Error", "Error al leer archivo",
                    "No se pudo leer el archivo de reporte: " + e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String encabezado, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.showAndWait();
    }


}