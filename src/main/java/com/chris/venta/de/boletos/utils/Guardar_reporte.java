package com.chris.venta.de.boletos.utils;

import com.chris.venta_de_boletos.Model.Reportes;
import java.io.FileWriter;
import java.io.IOException;

public class Guardar_reporte {

    public static void guardarEnArchivo(Reportes reporte, String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo, true)) {
            writer.write(reporte.toString());
            writer.write("\n--------------------\n");
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

}
