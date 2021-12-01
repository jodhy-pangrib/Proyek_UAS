package com.example.proyekuas;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyekuas.Room.Database.DatabaseAkun;
import com.example.proyekuas.Room.Entity.Akun;
import com.example.proyekuas.SharedPreferences.Preferences.UserPreferences;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    TextView here;
    MaterialButton btnlogin;
    private TextInputLayout username, password;
    private UserPreferences userPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userPreferences = new UserPreferences(LoginActivity.this);

        here = findViewById(R.id.here);
        btnlogin = findViewById(R.id.btnLogin);
        username = findViewById(R.id.inputLayoutUsername);
        password = findViewById(R.id.inputLayoutPassword);

        String text = "Don't have account ? Register Here";

        SpannableString spannableString = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#2A8FD1"));
                ds.setUnderlineText(false);
            }
        };

        spannableString.setSpan(clickableSpan,21,34, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        here.setText(spannableString);
        here.setMovementMethod(LinkMovementMethod.getInstance());

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm()) {
                    login();
                }
            }
        });
    }

    private boolean validateForm() {
        /* Check username & password is empty or not */
        if(username.getEditText().getText().toString().trim().isEmpty() || password.getEditText().getText().toString().trim().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Username Atau Password Kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void login() {
        class Login extends AsyncTask<Void, Void, Akun> {
            @Override
            protected Akun doInBackground(Void... voids) {
                Akun akun = DatabaseAkun.getInstance(LoginActivity.this)
                        .getDatabase()
                        .akunDao()
                        .login(username.getEditText().getText().toString().trim(),password.getEditText().getText().toString().trim());

                return akun;
            }

            @Override
            protected void onPostExecute(Akun akun) {
                super.onPostExecute(akun);

                if(akun == null) {
                    Toast.makeText(LoginActivity.this, "Akun tidak terdaftar!", Toast.LENGTH_SHORT).show();
                } else {
                    /* Set user to sharedPreferences */
                    userPreferences.setLogin(akun.getNama(), akun.getJenisKelamin(), akun.getAlamat(), akun.getEmail(), akun.getNoTelp(), akun.getUsername(), akun.getPassword(), akun.getUmur(), akun.getTypeRoom());
                    if(userPreferences.checkLogin()) {
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }
                }
            }
        }
        Login login = new Login();
        login.execute();
    }
}
