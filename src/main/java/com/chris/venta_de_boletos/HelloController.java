package com.chris.venta_de_boletos;

import com.chris.venta_de_boletos.Service.GestorBoletos;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.spreadsheet.Grid;

import java.util.Arrays;
import java.util.List;


public class HelloController {
    @FXML
    private GridPane gridTable;

    private GestorBoletos gestorBoletos = new GestorBoletos();
    private Button[][] botones = new Button[7][10];

    public void initialize() {

        List<String> asientos = gestorBoletos.insertarboletos(7, 10);

        int aux = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 10; j++) {
                botones[i][j] = new Button();
                gridTable.add(botones[i][j], i, j);
                botones[i][j].setText(asientos.get(aux));
                aux++;
                botones[i][j].setStyle("-fx-background-color: #0f0");

            }
        }

    }

    @FXML
    void detected(MouseDragEvent event) {

    }



}