package com.example.proyekuas.Room.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.proyekuas.Room.Entity.Akun;

import java.util.List;

@Dao
public interface AkunDao {
    @Query("SELECT EXISTS (SELECT * FROM Akun WHERE username=(:username) OR email=(:email))")
    Boolean check(String username, String email);

    @Insert
    void insertAkun(Akun akun);

    @Delete
    void deleteAkun(Akun akun);

    @Query("UPDATE Akun SET typeRoom=(:typeRoom) WHERE username=(:username)")
    void updateAkun(String typeRoom, String username);

    @Query("SELECT username FROM Akun WHERE username=(:username)")
    String getUser(String username);

    @Query("UPDATE Akun SET nama=(:nama), umur=(:umur), alamat=(:alamat), noTelp=(:noTelp), username=(:username), image=(:image) WHERE email=(:email)")
    void updateProfil(String nama, int umur, String alamat, String noTelp, String username, String image, String email);

    @Query("SELECT * FROM Akun WHERE username=(:username) and password=(:password)")
    Akun login(String username, String password);
}
