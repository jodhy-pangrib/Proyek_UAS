package com.example.proyekuas.Room.Database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseAkun {
    private Context context;
    private static DatabaseAkun databaseAkun;

    private AppDatabase database;

    public DatabaseAkun(Context context) {
        this.context = context;
        database = Room.databaseBuilder(context, AppDatabase.class, "akun").allowMainThreadQueries().build();
    }

    public static synchronized  DatabaseAkun getInstance(Context context) {
        if(databaseAkun == null) {
            databaseAkun = new DatabaseAkun(context);
        }
        return databaseAkun;
    }

    public AppDatabase getDatabase() { return database; }
}
