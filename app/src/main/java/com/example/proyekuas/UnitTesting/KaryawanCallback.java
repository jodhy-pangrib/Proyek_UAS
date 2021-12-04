package com.example.proyekuas.UnitTesting;

import com.example.proyekuas.Volley.models.Karyawan;

public interface KaryawanCallback {

    void onSuccess(boolean value, Karyawan karyawan);
    void onError();

}
