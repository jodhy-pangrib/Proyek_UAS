package com.example.proyekuas.Volley.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReservasiResponse {
    private String message;

    private Reservasi reservasi;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Reservasi getReservasi() {
        return reservasi;
    }

    public void setReservasi(Reservasi reservasi) {
        this.reservasi = reservasi;
    }
}
