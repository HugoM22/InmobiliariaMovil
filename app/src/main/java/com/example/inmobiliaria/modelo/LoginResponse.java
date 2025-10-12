package com.example.inmobiliaria.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    @SerializedName("token") private String toke;

    public String getToke() {
        return toke;
    }

    public void setToke(String toke) {
        this.toke = toke;
    }
}
