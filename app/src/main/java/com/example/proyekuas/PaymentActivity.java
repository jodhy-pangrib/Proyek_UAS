package com.example.proyekuas;

import static com.android.volley.Request.Method.POST;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyekuas.Room.Database.DatabaseAkun;
import com.example.proyekuas.SharedPreferences.Entity.User;
import com.example.proyekuas.SharedPreferences.Preferences.UserPreferences;
import com.example.proyekuas.Volley.api.ReservasiApi;
import com.example.proyekuas.Volley.models.Reservasi;
import com.example.proyekuas.Volley.models.ReservasiResponse;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {
    private User user;
    private UserPreferences userPreferences;
    TextView judul,hiUser,nama, checkin, checkout, roomType, harga;
    EditText cash;
    ImageView arrow;
    MaterialButton payment;
    String setHarga;
    private LinearLayout layoutLoading;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        queue = Volley.newRequestQueue(this);

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
        layoutLoading = findViewById(R.id.layout_loading);

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
                createReservasi();
                startActivity(new Intent(PaymentActivity.this, FinalPageActivity.class));
            }
        }
        Update update = new Update();
        update.execute();
    }
    public void createReservasi() {
        setLoading(true);
        String[] total = harga.getText().toString().trim().split(" ");

        Reservasi reservasi = new Reservasi(
                nama.getText().toString().trim(),
                roomType.getText().toString().trim(),
                checkin.getText().toString().trim(),
                checkout.getText().toString().trim(),
                Float.parseFloat(total[1])
        );

        // Membuat request baru untuk membuat data mahasiswa baru
        StringRequest stringRequest = new StringRequest(POST, ReservasiApi.ADD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API menjadi java object MahasiswaResponse menggunakan Gson */
                ReservasiResponse reservasiResponse = gson.fromJson(response, ReservasiResponse.class);

                Toast.makeText(PaymentActivity.this, reservasiResponse.getMessage(), Toast.LENGTH_SHORT).show();

                setLoading(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(PaymentActivity.this,errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(PaymentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            // Menambahkan header pada request
            @Override public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");

                return headers;
            }

            // Menambahkan request body berupa object mahasiswa
            @Override public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                /* Serialisasi data dari java object MahasiswaResponse menjadi JSON string menggunakan Gson */
                String requestBody = gson.toJson(reservasi);

                return requestBody.getBytes(StandardCharsets.UTF_8);
            }

            // Mendeklarasikan content type dari request body yang ditambahkan
            @Override public String getBodyContentType() {
                return "application/json";
            }
        };

        // Menambahkan request ke request queue
        queue.add(stringRequest);
    }

    private void setLoading(boolean isLoading) {
        if (isLoading) { getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.VISIBLE);
        } else { getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.INVISIBLE);
        }
    }
}