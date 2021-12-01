package com.example.proyekuas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.proyekuas.Geolocation.MapActivity;
import com.example.proyekuas.SharedPreferences.Preferences.UserPreferences;

public class MainActivity extends AppCompatActivity {
    TextView here;
    Button start;
    LinearLayout geolocation;
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        here = findViewById(R.id.here);
        start = findViewById(R.id.start);
        geolocation = findViewById(R.id.geolocation);

        userPreferences = new UserPreferences(MainActivity.this);

        checkLogin();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
            }
        });

        geolocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MapActivity.class));
            }
        });

        String text = "Do you have account ? Here";

        SpannableString spannableString = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#2A8FD1"));
                ds.setUnderlineText(false);
            }
        };

        spannableString.setSpan(clickableSpan,22,26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        here.setText(spannableString);
        here.setMovementMethod(LinkMovementMethod.getInstance());
    }
    private void checkLogin() {
        if(userPreferences.checkLogin()) {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }
    }
}