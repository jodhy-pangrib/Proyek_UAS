package com.example.proyekuas.SharedPreferences.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public static final String IS_START = "isStart";

    public AppPreferences(Context context) {
        this.context = context;
        /* penamaan bebas namun disini digunakan "userPreferences" */
        sharedPreferences = context.getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setStart() {
        editor.putBoolean(IS_START, true);
        editor.commit();
    }

    public boolean checkStart() {
        /* Mengembalikan nilai is_login, jika sudah lgoin otomatis nilai true. Jika tidak akan return false */
        return sharedPreferences.getBoolean(IS_START,false);
    }

    public void exit() {
        /* Melakukan clear data yang ada pada sharedPreferences, jangan lupa di commit agar data terubah */
        editor.clear();
        editor.commit();
    }
}
