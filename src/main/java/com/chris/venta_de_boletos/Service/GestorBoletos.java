package com.chris.venta_de_boletos.Service;

import com.chris.venta_de_boletos.Model.Boletos;
import com.chris.venta_de_boletos.Model.Reportes;

import java.util.*;

public class GestorBoletos {

    private static final int maxReportes = 100;

    private LinkedList<Boletos> boletosGuadados;
    private Boolean[][] asientosDisponibles;
    private HashMap<String, Integer> categoriasSeleccionadas;
    private Cola reportesVentas;

    public List<String> insertarboletos(int fila, int columna) {
        boletosGuadados = new LinkedList<>();
        asientosDisponibles = new Boolean[fila][columna];
        categoriasSeleccionadas = new HashMap<>();
        reportesVentas = new Cola(maxReportes);

        categoriasSeleccionadas.put("VIP", 1000);
        categoriasSeleccionadas.put("GENERAL", 500);
        categoriasSeleccionadas.put("PREFERENCIAL", 250);

        List<String> asientoBotones = new ArrayList<>();

        char Asiento = 'A';
        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                String categoria;
                if (i < 3) {
                    categoria = "VIP";
                }
                if (i > 2 && i < 5) {
                    categoria = "GENERAL";
                } else {
                    categoria = "PREFERENCIAL";
                }

                asientoBotones.add(Asiento + "" + (j + 1));

                Boletos boletos = new Boletos(
                        UUID.randomUUID().toString(),
                        categoria,
                        categoriasSeleccionadas.get(categoria),
                        0,
                        true
                );
                boletosGuadados.add(boletos);
            }
            Asiento++;
        }
        return asientoBotones;
    }

    private void reporteDeVenta(List<Boletos> boletosVendidos, String categoria) {
        double ingresoTotal = 0;
        for (Boletos boleto : boletosVendidos) {
            ingresoTotal += boleto.getPrecio();

        }
        Reportes nuevoReporte = new Reportes(java.time.LocalDateTime.now(),
                categoria,
                boletosVendidos.size(),
                ingresoTotal,
                boletosVendidos);

        reportesVentas.insert(nuevoReporte);

    }

    private void guardarReporteArchivo() {
        while (!reportesVentas.isEmpty()) {
            Reportes reporte = reportesVentas.remove();
            com.chris.venta.de.boletos.utils.Guardar_reporte.guardarEnArchivo(reporte, "reportes_ventas.txt");

        }

    }

}
