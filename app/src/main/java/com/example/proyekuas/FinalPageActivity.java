package com.example.proyekuas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.proyekuas.SharedPreferences.Entity.User;
import com.example.proyekuas.SharedPreferences.Preferences.UserPreferences;
import com.google.android.material.button.MaterialButton;

public class FinalPageActivity extends AppCompatActivity {
    MaterialButton okay;
    TextView wehope;
    private User user;
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_page);

        okay = findViewById(R.id.okay);
        wehope = findViewById(R.id.wehope);

        userPreferences = new UserPreferences(FinalPageActivity.this);
        user = userPreferences.getUserLogin();

        wehope.setText("We hope you enjoy your\nroom and your holiday\n\nHave fun "+user.getNama());

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FinalPageActivity.this, HomeActivity.class));
                finish();
            }
        });
    }
}