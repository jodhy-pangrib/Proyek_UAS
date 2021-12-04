package com.example.proyekuas.Volley.models;

import java.util.Date;

public class Reservasi {

    private Long id;
    private String nama;
    private String room_type;
    private String checkIn;
    private String checkOut;
    private float total_harga;

    public Reservasi (String nama, String room_type, String checkIn, String checkOut, float total_harga) {
        this.nama = nama;
        this.room_type = room_type;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.total_harga = total_harga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public float getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(float total_harga) {
        this.total_harga = total_harga;
    }
}
