package com.example.proyekuas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyekuas.SharedPreferences.Entity.User;
import com.example.proyekuas.SharedPreferences.Preferences.UserPreferences;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ReservationActivity extends AppCompatActivity {
    private User user;
    private UserPreferences userPreferences;
    ImageView imageRoom, arrow;
    TextView typeRoom, hiUser, judul, checkIn, checkOut;
    MaterialButton submit;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        arrow = findViewById(R.id.arrow);
        imageRoom = findViewById(R.id.imageRoom);
        typeRoom = findViewById(R.id.isiRoomType);
        hiUser = findViewById(R.id.hiUser);
        judul = findViewById(R.id.judul);
        checkIn = findViewById(R.id.checkIn);
        checkOut = findViewById(R.id.checkOut);
        submit = findViewById(R.id.submit);
        String imageUrl = getIntent().getStringExtra("urlGambar");

        typeRoom.setText(getIntent().getStringExtra("typeRoom"));

        Glide.with(ReservationActivity.this)
                .setDefaultRequestOptions(new RequestOptions())
                .load(imageUrl)
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(android.R.drawable.stat_notify_error)
                .into(imageRoom);

        userPreferences = new UserPreferences(ReservationActivity.this);
        user = userPreferences.getUserLogin();
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        hiUser.setText("Hi, "+user.getNama()+"\nComplete Your Data");
        judul.setText("Reservation");

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReservationActivity.this, HomeActivity.class));
                finish();
            }
        });

        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar newCalendar = Calendar.getInstance();
                datePickerDialog     = new DatePickerDialog(ReservationActivity.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        checkIn.setText(dateFormatter.format(newDate.getTime()));
                    }
                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar newCalendar = Calendar.getInstance();
                datePickerDialog     = new DatePickerDialog(ReservationActivity.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        checkOut.setText(dateFormatter.format(newDate.getTime()));
                    }
                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String checkkIn = checkIn.getText().toString();
                    String checkkOut = checkOut.getText().toString();
                    if(checkkIn.isEmpty() || checkkOut.isEmpty()) {
                        Toast.makeText(ReservationActivity.this, "Isi Tanggal Dulu!!!",Toast.LENGTH_SHORT).show();
                    } else {
                        SimpleDateFormat dateFormatterCheck = new SimpleDateFormat("yyyy-MM-dd");

                        Date tempCurrent = Calendar.getInstance().getTime();
                        String formatCurrent = dateFormatterCheck.format(tempCurrent);

                        Date dateCheckIn = dateFormatterCheck.parse(checkkIn);
                        Date dateCheckOut = dateFormatterCheck.parse(checkkOut);
                        Date current = dateFormatterCheck.parse(formatCurrent);

                        long check1 =  dateCheckIn.getTime() - current.getTime();
                        long check2 = dateCheckOut.getTime() - current.getTime();
                        long diff = dateCheckOut.getTime() - dateCheckIn.getTime();
                        long days = (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                        long days2 = (TimeUnit.DAYS.convert(check1, TimeUnit.MILLISECONDS));
                        long days3 = (TimeUnit.DAYS.convert(check2, TimeUnit.MILLISECONDS));

                        if(days2 < 0 || days3 < 0) {
                            Toast.makeText(ReservationActivity.this, "Inputan Tanggal Invalid!!!",Toast.LENGTH_SHORT).show();
                        } else if(days < 0) {
                            Toast.makeText(ReservationActivity.this, "Check In tidak boleh lebih dari Check Out",Toast.LENGTH_SHORT).show();
                        } else if(days == 0) {
                            Toast.makeText(ReservationActivity.this, "Minimal Nginap 1 Hari!!!",Toast.LENGTH_SHORT).show();
                        } else {
                            String harga = getIntent().getStringExtra("harga");
                            Intent intent = new Intent(ReservationActivity.this, PaymentActivity.class);
                            intent.putExtra("harga",harga);
                            intent.putExtra("checkIn",checkkIn);
                            intent.putExtra("checkOut", checkkOut);
                            intent.putExtra("typeRoom", typeRoom.getText().toString());
                            intent.putExtra("days", String.valueOf(days));
                            startActivity(intent);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}