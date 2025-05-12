package com.chris.venta_de_boletos.Model;

import java.util.UUID;

public class Boletos {
    private String id;
    private String categoria;
    private double precio;
    private String asiento; // Formato "Fila-Columna" ej. "A-1"
    private boolean vendido;

    // Constructor, getters y setters
    public Boletos(String id, String categoria, double precio, String asiento) {
        this.id = id;
        this.categoria = categoria;
        this.precio = precio;
        this.asiento = asiento;
        this.vendido = false;
    }

    // Métodos adicionales...


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public boolean isVendido() {
        return vendido;
    }

    public void setVendido(boolean vendido) {
        this.vendido = vendido;
    }
}