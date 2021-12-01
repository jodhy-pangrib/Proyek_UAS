package com.example.proyekuas.SharedPreferences.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.proyekuas.SharedPreferences.Entity.User;

public class UserPreferences {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    /* Mendefinisikan key */
    public static final String IS_LOGIN = "isLogin";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NAMA = "nama";
    public static final String KEY_JENISKELAMIN = "jenisKelamin";
    public static final String KEY_ALAMAT = "alamat";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NOTELP = "noTelp";
    public static final String KEY_UMUR = "umur";
    public static final String KEY_ROOM = "room";



    public UserPreferences(Context context) {
        this.context = context;
        /* penamaan bebas namun disini digunakan "userPreferences" */
        sharedPreferences = context.getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(String nama, String jenisKelamin, String alamat, String email, String noTelp, String username, String password, int umur, String room) {
        /*Menyumpan data lagin ke sharedPreferences dengan key dan value */
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAMA, nama);
        editor.putString(KEY_JENISKELAMIN, jenisKelamin);
        editor.putString(KEY_ALAMAT, alamat);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_NOTELP, noTelp);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putInt(KEY_UMUR, umur);
        editor.putString(KEY_ROOM, room);

        /* Jangan lupa commit karena kalo hanya set editornya saja dan tidak commit akan sia-sia */
        editor.commit();
    }

    public User getUserLogin() {
        /* Mengembalikan object User untuk menampilkan data user jika user sudah login */
        String username, password, nama, jenisKelamin, alamat, email, noTelp,room;
        int umur;

        nama = sharedPreferences.getString(KEY_NAMA, null);
        jenisKelamin = sharedPreferences.getString(KEY_JENISKELAMIN, null);
        alamat = sharedPreferences.getString(KEY_ALAMAT, null);
        email = sharedPreferences.getString(KEY_EMAIL, null);
        noTelp = sharedPreferences.getString(KEY_NOTELP, null);
        username = sharedPreferences.getString(KEY_USERNAME,null);
        password = sharedPreferences.getString(KEY_PASSWORD,null);
        umur = sharedPreferences.getInt(KEY_UMUR, -1);
        room = sharedPreferences.getString(KEY_ROOM,null);

        return new User(nama,jenisKelamin,alamat, email,noTelp,username,password,umur,room);
    }

    public boolean checkLogin() {
        /* Mengembalikan nilai is_login, jika sudah lgoin otomatis nilai true. Jika tidak akan return false */
        return sharedPreferences.getBoolean(IS_LOGIN,false);
    }

    public void logout() {
        /* Melakukan clear data yang ada pada sharedPreferences, jangan lupa di commit agar data terubah */
        editor.clear();
        editor.commit();
    }
}
