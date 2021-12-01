package com.example.proyekuas.Room.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.proyekuas.Room.Dao.AkunDao;
import com.example.proyekuas.Room.Entity.Akun;

@Database(entities = {Akun.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AkunDao akunDao();
}
