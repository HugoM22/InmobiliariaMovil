package com.example.inmobiliaria.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Propietario implements Serializable {
    @SerializedName("id") private int id;
    @SerializedName("dni") private int dni;
    @SerializedName("nombre") private String nombre;
    @SerializedName("apellido") private String apellido;
    @SerializedName("email") private String email;
    @SerializedName("tel") private String tel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
