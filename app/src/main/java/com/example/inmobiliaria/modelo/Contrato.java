package com.example.inmobiliaria.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Contrato implements Serializable {
    @SerializedName("id") private int id;
    @SerializedName("inmuebleId") private int inmuebleId;
    @SerializedName("fechaInicio") private String fechaInicio; // igual
    @SerializedName("fechaFin") private String fechaFin; //Fijarme el tema de fecha
    @SerializedName("precioMensual") private double precioMensual;
    @SerializedName("estado") private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getPrecioMensual() {
        return precioMensual;
    }

    public void setPrecioMensual(double precioMensual) {
        this.precioMensual = precioMensual;
    }

    public int getInmuebleId() {
        return inmuebleId;
    }

    public void setInmuebleId(int inmuebleId) {
        this.inmuebleId = inmuebleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
}
