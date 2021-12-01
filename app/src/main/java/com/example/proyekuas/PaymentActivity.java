package com.example.proyekuas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyekuas.Room.Database.DatabaseAkun;
import com.example.proyekuas.SharedPreferences.Entity.User;
import com.example.proyekuas.SharedPreferences.Preferences.UserPreferences;
import com.google.android.material.button.MaterialButton;

public class PaymentActivity extends AppCompatActivity {
    private User user;
    private UserPreferences userPreferences;
    TextView judul,hiUser,nama, checkin, checkout, roomType, harga;
    EditText cash;
    ImageView arrow;
    MaterialButton payment;
    String setHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        judul = findViewById(R.id.judul);
        hiUser = findViewById(R.id.hiUsernamePayment);
        nama = findViewById(R.id.isiNamaPayment);
        checkin = findViewById(R.id.isiCheckInPayment);
        checkout = findViewById(R.id.isiCheckOutPayment);
        roomType = findViewById(R.id.isiRoomTypePayment);
        harga = findViewById(R.id.isiTotalPricePayment);
        cash = findViewById(R.id.yourCashPayment);
        arrow = findViewById(R.id.arrow);
        payment = findViewById(R.id.payment);

        userPreferences = new UserPreferences(PaymentActivity.this);
        user = userPreferences.getUserLogin();
        judul.setText("Payment");
        hiUser.setText("Hi, "+user.getNama()+"\nComplete Your Payment");
        nama.setText(user.getNama());

        roomType.setText(getIntent().getStringExtra("typeRoom"));
        checkin.setText(getIntent().getStringExtra("checkIn"));
        checkout.setText(getIntent().getStringExtra("checkOut"));

        double price = Double.parseDouble(getIntent().getStringExtra("harga"));
        double days = Double.parseDouble(getIntent().getStringExtra("days"));
        price = days*price;
        setHarga = String.valueOf(price);
        harga.setText("Rp. "+setHarga);

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cash.getText().toString().isEmpty()) {
                    Toast.makeText(PaymentActivity.this, "Pembayaran Harus Diisi!!!",Toast.LENGTH_SHORT).show();
                } else {
                    double bayar = Double.parseDouble(cash.getText().toString().trim());
                    double price = Double.parseDouble(setHarga);
                    if(bayar < price) {
                        Toast.makeText(PaymentActivity.this, "Uang Kurang!!!",Toast.LENGTH_SHORT).show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
                        builder.setTitle("Pembayaran Berhasil")
                                .setMessage("Uang Kembali : Rp. "+(bayar-price))
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        update(roomType.getText().toString().trim(),user.getUsername());
                                    }
                                })
                                .setCancelable(true)
                                .create().show();
                    }
                }
            }
        });

    }

    private void update(String typeRoom, String username) {
        class Update extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseAkun.getInstance(PaymentActivity.this)
                        .getDatabase()
                        .akunDao()
                        .updateAkun(typeRoom,username);

                return null;
            }

            @Override
            protected  void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                userPreferences.setLogin(user.getNama(),user.getJenisKelamin(),user.getAlamat(),user.getEmail(),user.getNoTelp(),user.getUsername(),user.getPassword(),user.getUmur(),roomType.getText().toString().trim());
                startActivity(new Intent(PaymentActivity.this, FinalPageActivity.class));
            }
        }
        Update update = new Update();
        update.execute();
    }
}