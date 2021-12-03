package com.example.proyekuas.Volley.models;

import java.util.Date;

public class Reservasi {

    private String nama;
    private String room_type;
    private Date checkIn;
    private Date checkOut;
    private float total_harga;

    public Reservasi (String nama, String room_type, Date checkIn, Date checkOut, float total_harga) {
        this.nama = nama;
        this.room_type = room_type;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.total_harga = total_harga;
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

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public float getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(float total_harga) {
        this.total_harga = total_harga;
    }
}
