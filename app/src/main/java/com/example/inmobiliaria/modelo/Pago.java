package com.example.inmobiliaria.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pago implements Serializable {
    private int idPago;
    private int nropago;
    private String fechaPago;
    private double monto;
    private String detalle;
    private boolean estado;
    private int idContrato;

    public Pago() {
    }

    public Pago(int idPago, int idContrato, boolean estado, String detalle, double monto, String fechaPago, int nropago) {
        this.idPago = idPago;
        this.idContrato = idContrato;
        this.estado = estado;
        this.detalle = detalle;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.nropago = nropago;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public int getNropago() {
        return nropago;
    }

    public void setNropago(int nropago) {
        this.nropago = nropago;
    }
}
