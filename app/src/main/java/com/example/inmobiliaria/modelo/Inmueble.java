package com.example.inmobiliaria.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Inmueble implements Serializable {
    @SerializedName("id") private int id;
    @SerializedName("direccion") private String dirrecion;
    @SerializedName("ambientes") private int ambientes;
    @SerializedName("tipo") private String tipo;
    @SerializedName("uso") private String uso;
    @SerializedName("precio") private double precio;
    @SerializedName("disponible") private boolean disponible;
    @SerializedName("idPropietario") private int idPropietario;
    @SerializedName("fotoUrl") private String fotoUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDirrecion() {
        return dirrecion;
    }

    public void setDirrecion(String dirrecion) {
        this.dirrecion = dirrecion;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
