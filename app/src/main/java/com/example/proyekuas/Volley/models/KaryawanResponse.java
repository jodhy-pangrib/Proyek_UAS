package com.example.proyekuas.Volley.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KaryawanResponse {
    private String message;

    private Karyawan karyawan;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Karyawan getKaryawan() {
        return karyawan;
    }

    public void setKaryawan(Karyawan karyawan) {
        this.karyawan = karyawan;
    }
}
