package com.chris.venta_de_boletos.Model;

import java.time.LocalDateTime;
import java.util.List;

public class Reportes {
    private LocalDateTime fecha;

    private String categoria;

    private Integer boletosVentidos;

    private double ingresoTotal;

    private List<Boletos> boletos;

    public Reportes() {
    }


    public Reportes(LocalDateTime fecha, String categoria, Integer boletosVentidos, double ingresoTotal, List<Boletos> boletos) {
        this.fecha = fecha;
        this.categoria = categoria;
        this.boletosVentidos = boletosVentidos;
        this.ingresoTotal = ingresoTotal;
        this.boletos = boletos;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getBoletosVentidos() {
        return boletosVentidos;
    }

    public void setBoletosVentidos(Integer boletosVentidos) {
        this.boletosVentidos = boletosVentidos;
    }

    public double getIngresoTotal() {
        return ingresoTotal;
    }

    public void setIngresoTotal(double ingresoTotal) {
        this.ingresoTotal = ingresoTotal;
    }

    public List<Boletos> getBoletos() {
        return boletos;
    }

    public void setBoletos(List<Boletos> boletos) {
        this.boletos = boletos;
    }
}


