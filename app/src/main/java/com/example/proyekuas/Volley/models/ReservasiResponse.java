package com.example.proyekuas.Volley.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ReservasiResponse {

    private String message;

    @SerializedName("Reservasi")
    private List<Reservasi> reservasiList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Reservasi> getReservasiList() {
        return reservasiList;
    }

    public void setReservasiList(List<Reservasi> reservasiList) {
        this.reservasiList = reservasiList;
    }
}
