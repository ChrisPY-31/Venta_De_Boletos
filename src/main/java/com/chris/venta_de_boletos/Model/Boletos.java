package com.chris.venta_de_boletos.Model;

import java.util.UUID;

public class Boletos {
    private String idBoleto;
    private String categoria;
    private double precio;
    private Integer numeroAsientos;
    private Boolean estado;

    public Boletos() {

    }

    public Boletos(String idBoleto ,String categoria, double precio, Integer numeroAsientos, Boolean estado) {
        this.idBoleto = idBoleto;
        this.categoria = categoria;
        this.precio = precio;
        this.numeroAsientos = numeroAsientos;
        this.estado = estado;
    }

    public String getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(String idBoleto) {
        this.idBoleto = idBoleto;
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

    public Integer getNumeroAsientos() {
        return numeroAsientos;
    }

    public void setNumeroAsientos(Integer numeroAsientos) {
        this.numeroAsientos = numeroAsientos;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String toString(){
        return ("" +
                "id" +getIdBoleto()+
                "\ncategoria" + getCategoria()+
                "\nprecio" + getPrecio() +
                "\nnumeroAsientos" + getNumeroAsientos() + "");
    }
}
