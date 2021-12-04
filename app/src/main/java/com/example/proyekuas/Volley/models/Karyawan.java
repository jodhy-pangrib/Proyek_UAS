package com.example.proyekuas.Volley.models;

public class Karyawan {
    private Long id;
    private String nama_karyawan;
    private String nomor_karyawan;
    private Integer umur;
    private String jenis_kelamin;
    private String role;

    public Karyawan(String nama_karyawan, String nomor_karyawan, Integer umur, String jenis_kelamin, String role) {
        this.nama_karyawan = nama_karyawan;
        this.nomor_karyawan = nomor_karyawan;
        this.umur = umur;
        this.jenis_kelamin = jenis_kelamin;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama_karyawan() {
        return nama_karyawan;
    }

    public void setNama_karyawan(String nama_karyawan) {
        this.nama_karyawan = nama_karyawan;
    }

    public String getNomor_karyawan() {
        return nomor_karyawan;
    }

    public void setNomor_karyawan(String nomor_karyawan) {
        this.nomor_karyawan = nomor_karyawan;
    }

    public Integer getUmur() {
        return umur;
    }

    public void setUmur(Integer umur) {
        this.umur = umur;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
