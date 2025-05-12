package com.chris.venta_de_boletos.Service;

import com.chris.venta_de_boletos.Model.Boletos;
import com.chris.venta_de_boletos.Model.Reportes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GestorBoletos {


    private Map<String, LinkedList<Boletos>> boletosPorCategoria;
    private Map<String, Double> preciosCategorias;
    private Queue<String> reportesVentas;
    private boolean[][] asientosOcupados; // Matriz de asientos

    public GestorBoletos(int filas, int columnas) {
        boletosPorCategoria = new HashMap<>();
        preciosCategorias = new HashMap<>();
        reportesVentas = new LinkedList<>();
        asientosOcupados = new boolean[filas][columnas];
        preciosCategorias.put("VIP", 500.0);
        preciosCategorias.put("Preferencial", 300.0);
        preciosCategorias.put("General", 150.0);

        for (String categoria : preciosCategorias.keySet()) {
            boletosPorCategoria.put(categoria, new LinkedList<>());
        }

        inicializarAsientos(filas, columnas);
    }


    private void inicializarAsientos(int filas, int columnas) {
        char fila = 'A';
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                String asiento = fila + "-" + (j + 1);
                String categoria;
                if (i < 2) categoria = "VIP";
                else if (i < 5) categoria = "Preferencial";
                else categoria = "General";
                Boletos boleto = new Boletos(
                        UUID.randomUUID().toString(),
                        categoria,
                        preciosCategorias.get(categoria),
                        asiento
                );

                boletosPorCategoria.get(categoria).add(boleto);
            }
            fila++;
        }
    }


    public boolean comprarBoleto(String categoria, String asiento) {
        if (!preciosCategorias.containsKey(categoria)) {
            throw new IllegalArgumentException("Categoría inválida: " + categoria);
        }

        if (asiento == null || !asiento.matches("^[A-Za-z]-\\d+$")) {
            throw new IllegalArgumentException("Formato de asiento inválido. Use Letra-Número (ej. A-1)");
        }

        String[] partes = asiento.split("-");
        char filaChar = partes[0].toUpperCase().charAt(0);
        int columna;

        try {
            columna = Integer.parseInt(partes[1]) - 1; // Convertir a índice base 0
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Número de asiento inválido: " + partes[1]);
        }

        int fila = filaChar - 'A';

        if (fila < 0 || fila >= asientosOcupados.length ||
                columna < 0 || columna >= asientosOcupados[0].length) {
            throw new IllegalArgumentException("Asiento fuera de rango: " + asiento);
        }

        if (asientosOcupados[fila][columna]) {
            return false;
        }

        LinkedList<Boletos> boletosCategoria = boletosPorCategoria.get(categoria);
        Boletos boletoEncontrado = null;

        for (Boletos boleto : boletosCategoria) {
            if (boleto.getAsiento().equalsIgnoreCase(asiento)) {
                boletoEncontrado = boleto;
                break;
            }
        }

        if (boletoEncontrado == null) {
            throw new IllegalStateException("Inconsistencia de datos: Boleto no encontrado para asiento " + asiento);
        }

        if (boletoEncontrado.isVendido()) {
            return false;
        }


        synchronized (this) {
            // Doble verificación por si otro hilo modificó el estado
            if (!asientosOcupados[fila][columna] && !boletoEncontrado.isVendido()) {
                boletoEncontrado.setVendido(true);
                asientosOcupados[fila][columna] = true;
                return true;
            }
        }

        return false;
    }

    public String generarReporteVenta(String categoria, String asiento, double total) {
        // Generar reporte y agregar a la cola
        String reporte = "Fecha: " + LocalDateTime.now() + "\n" +
                "Categoría: " + categoria + "\n" +
                "Asiento: " + asiento + "\n" +
                "Total: $" + total;

        reportesVentas.add(reporte);
        guardarReporteEnArchivo(reporte);
        return reporte;
    }


    private void guardarReporteEnArchivo(String reporte) {
        String nombreArchivo = "reporte_ventas_" +
                LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy")) +
                ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            writer.write(reporte);
            writer.newLine();
            writer.write("-------------------------");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters para las estructuras de datos
    public Map<String, Double> getPreciosCategorias() {
        return preciosCategorias;
    }

    public boolean[][] getAsientosOcupados() {
        return asientosOcupados;

    }

}
