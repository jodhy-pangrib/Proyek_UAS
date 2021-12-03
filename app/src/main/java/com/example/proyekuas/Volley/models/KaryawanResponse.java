package com.example.proyekuas.Volley.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KaryawanResponse {
    private String message;

    @SerializedName("karyawan")
    private List<Karyawan> karyawanList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Karyawan> getKaryawanList() {
        return karyawanList;
    }

    public void setKaryawanList(List<Karyawan> karyawanList) {
        this.karyawanList = karyawanList;
    }
}
