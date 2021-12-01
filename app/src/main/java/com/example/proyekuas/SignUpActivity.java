package com.example.proyekuas;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyekuas.Room.Database.DatabaseAkun;
import com.example.proyekuas.Room.Entity.Akun;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    ImageView arrow;
    TextView judul, nama, alamat, email, noTelp, username, password, umur;
    RadioGroup radioGroup;
    MaterialButton cancel, signup;
    String jenisKelamin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        arrow = findViewById(R.id.arrow);
        judul = findViewById(R.id.judul);
        nama = findViewById(R.id.nama);
        umur = findViewById(R.id.umur);
        alamat = findViewById(R.id.alamat);
        email = findViewById(R.id.email);
        noTelp = findViewById(R.id.telepon);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        radioGroup = findViewById(R.id.radioGroup);
        cancel = findViewById(R.id.btnClear);
        signup = findViewById(R.id.btnSignUp);

        judul.setText("SIGN UP");

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioGroup.clearCheck();
                nama.setText("");
                alamat.setText("");
                umur.setText("");
                email.setText("");
                noTelp.setText("");
                username.setText("");
                password.setText("");
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cek = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(cek);
                if(cekKosong(cek)) {
                    String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if(Integer.parseInt(umur.getText().toString().trim())<18) {
                        Toast.makeText(SignUpActivity.this, "Umur Tidak Cukup!", Toast.LENGTH_SHORT).show();
                    } else if(!email.getText().toString().trim().matches(pattern)) {
                        Toast.makeText(SignUpActivity.this, "Email Invalid!", Toast.LENGTH_SHORT).show();
                    } else if(noTelp.getText().toString().trim().length()<10 || noTelp.getText().toString().trim().length()>13) {
                        Toast.makeText(SignUpActivity.this, "Nomor Hp 10-13 Digit!", Toast.LENGTH_SHORT).show();
                    } else {
                        jenisKelamin = radioButton.getText().toString();
                        getAkun(username.getText().toString().trim(), email.getText().toString().trim());
                    }
                }
            }
        });
    }

    public boolean cekKosong(int cek) {
        if(nama.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty()
                || username.getText().toString().trim().isEmpty()
                || alamat.getText().toString().trim().isEmpty()
                || email.getText().toString().trim().isEmpty()
                || noTelp.getText().toString().trim().isEmpty()
                || umur.getText().toString().trim().isEmpty()
                || cek == -1) {
            Toast.makeText(SignUpActivity.this, "Data Masih Kosong!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void addAkun(String gender) {
        final String name = nama.getText().toString();
        final String usia = umur.getText().toString();
        final String alamatUser = alamat.getText().toString();
        final String emailUser = email.getText().toString();
        final String telp = noTelp.getText().toString();
        final String user = username.getText().toString();
        final String pass = password.getText().toString();
        final String typeRoom = "-";

        class AddTodo extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                Akun akun = new Akun(name,gender,alamatUser,emailUser,telp,user,pass,Integer.parseInt(usia),typeRoom);

                DatabaseAkun.getInstance(SignUpActivity.this)
                        .getDatabase()
                        .akunDao()
                        .insertAkun(akun);

                return null;
            }

            @Override
            protected  void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Toast.makeText(SignUpActivity.this, "Register Berhasil. Tolong cek email untuk verifikasi", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
        }
        AddTodo addTodo = new AddTodo();
        addTodo.execute();
    }

    public void getAkun(String username, String email) {
        class CheckAkun extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected Boolean doInBackground(Void... voids) {
                Boolean check = DatabaseAkun.getInstance(SignUpActivity.this)
                        .getDatabase()
                        .akunDao()
                        .check(username, email);

                return check;
            }

            @Override
            protected void onPostExecute(Boolean check) {
                super.onPostExecute(check);
                if(check) {
                    Toast.makeText(SignUpActivity.this, "Username atau Email sudah ada!", Toast.LENGTH_SHORT).show();
                } else {
                    createUser();
                }
            }
        }
        CheckAkun checkAkun = new CheckAkun();
        checkAkun.execute();
    }

    public void createUser() {
        String emailAuth = email.getText().toString();
        String passwordAuth = password.getText().toString();
        mAuth.createUserWithEmailAndPassword(emailAuth, passwordAuth)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        addAkun(jenisKelamin);
                                    } else {
                                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
