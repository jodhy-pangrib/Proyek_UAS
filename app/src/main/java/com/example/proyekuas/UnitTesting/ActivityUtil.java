package com.example.proyekuas.UnitTesting;

import android.content.Context;
import android.content.Intent;

import com.example.proyekuas.AddEditKaryawan;

public class ActivityUtil {
    private Context context;

    public ActivityUtil(Context context) { this.context = context; }

    public void startMainAddEditKaryawan() {
        context.startActivity(new Intent(context, AddEditKaryawan.class));
    }
}
