package com.example.proyekuas.dataBinding;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DataHotel {
    public String namaKamar, fasilitas1, fasilitas2, fasilitas3, keterangan, imgURL;
    public double harga;

    public DataHotel(String namaKamar, String fasilitas1, String fasilitas2, String fasilitas3, String keterangan, double harga, String imgURL) {
        this.namaKamar = namaKamar;
        this.fasilitas1 = fasilitas1;
        this.fasilitas2 = fasilitas2;
        this.fasilitas3 = fasilitas3;
        this.keterangan = keterangan;
        this.harga = harga;
        this.imgURL = imgURL;
    }

    public String getNamaKamar() {
        return namaKamar;
    }

    public void setNamaKamar(String namaKamar) {
        this.namaKamar = namaKamar;
    }

    public String getFasilitas1() {
        return fasilitas1;
    }

    public void setFasilitas1(String fasilitas1) {
        this.fasilitas1 = fasilitas1;
    }

    public String getFasilitas2() {
        return fasilitas2;
    }

    public void setFasilitas2(String fasilitas2) {
        this.fasilitas2 = fasilitas2;
    }

    public String getFasilitas3() {
        return fasilitas3;
    }

    public void setFasilitas3(String fasilitas3) {
        this.fasilitas3 = fasilitas3;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getStringHarga() { return String.valueOf(harga); }
    public void setStringHarga(String harga){
        if(harga.isEmpty()){
            this.harga = 0;
        }else{
            this.harga = Double.parseDouble(harga);
        }
    }

    // important code for loading image here
    @BindingAdapter({ "imgURL" })
    public static void loadImage(ImageView imageView, String imageURL) {
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions())
                .load(imageURL)
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(android.R.drawable.stat_notify_error)
                .into(imageView);
    }
}
