package com.example.proyekuas.Room.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.proyekuas.Room.Entity.Akun;

@Dao
public interface AkunDao {
    @Query("SELECT EXISTS (SELECT * FROM Akun WHERE username=(:username) OR email=(:email))")
    Boolean check(String username, String email);

    @Insert
    void insertAkun(Akun akun);

    @Delete
    void deleteAku(Akun akun);

    @Query("UPDATE Akun SET typeRoom=(:typeRoom) WHERE username=(:username)")
    void updateAkun(String typeRoom, String username);

    @Query("SELECT * FROM Akun WHERE username=(:username) and password=(:password)")
    Akun login(String username, String password);
}
