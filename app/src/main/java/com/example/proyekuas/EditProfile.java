package com.example.proyekuas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyekuas.Room.Database.DatabaseAkun;
import com.example.proyekuas.Room.Entity.Akun;
import com.example.proyekuas.SharedPreferences.Entity.User;
import com.example.proyekuas.SharedPreferences.Preferences.UserPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CAMERA = 100;
    private static final int CAMERA_REQUEST = 0;
    private static final int GALLERY_PICTURE = 1;

    private Bitmap bitmap = null;
    private CircleImageView photoProfile;
    private User user;
    private UserPreferences userPreferences;
    TextInputLayout  nama, umur, alamat, telp, username;
    TextView judul;
    ImageView arrow;
    MaterialButton cancel, submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        userPreferences = new UserPreferences(EditProfile.this);

        photoProfile = findViewById(R.id.photoProfile);
        nama = findViewById(R.id.inputLayoutNama);
        umur = findViewById(R.id.inputLayoutUmur);
        alamat = findViewById(R.id.inputLayoutAlamat);
        telp = findViewById(R.id.inputLayoutNoTelp);
        username = findViewById(R.id.inputLayoutUsername);
        cancel = findViewById(R.id.btnCancel);
        submit = findViewById(R.id.btnSubmit);
        judul = findViewById(R.id.judul);
        arrow = findViewById(R.id.arrow);

        judul.setText("Edit Profil");
        user = userPreferences.getUserLogin();
        getAkun(user.getUsername(), user.getPassword());

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cekKosong()) {
                    if(Integer.parseInt(umur.getEditText().getText().toString().trim())<18) {
                        Toast.makeText(EditProfile.this, "Umur Tidak Cukup!", Toast.LENGTH_SHORT).show();
                    } else if(telp.getEditText().getText().toString().trim().length()<10 || telp.getEditText().getText().toString().trim().length()>13) {
                        Toast.makeText(EditProfile.this, "Nomor Hp 10-13 Digit!", Toast.LENGTH_SHORT).show();
                    } else {
                        getUser(username.getEditText().getText().toString().trim());
                    }
                }
            }
        });

        photoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(EditProfile.this);
                View selectMediaView = layoutInflater.inflate(R.layout.layout_select_media, null);

                final AlertDialog alertDialog = new AlertDialog.Builder(selectMediaView.getContext()).create();

                Button btnKamera = selectMediaView.findViewById(R.id.btn_kamera);
                Button btnGaleri = selectMediaView.findViewById(R.id.btn_galeri);

                btnKamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkSelfPermission(Manifest.permission.CAMERA) ==
                                PackageManager.PERMISSION_DENIED) {
                            String[] permission = {Manifest.permission.CAMERA};
                            requestPermissions(permission, PERMISSION_REQUEST_CAMERA);
                        } else {
                            // Membuka kamera
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, CAMERA_REQUEST);
                        }
                        alertDialog.dismiss();
                    }
                });

                btnGaleri.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // Membuka galeri
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, GALLERY_PICTURE);

                        alertDialog.dismiss();
                    }
                });

                alertDialog.setView(selectMediaView);
                alertDialog.show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Membuka kamera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);
            } else {
                Toast.makeText(EditProfile.this, "Permission denied.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null)
            return;

        if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE) {
            Uri selectedImage = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                Toast.makeText(EditProfile.this, e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
            bitmap = (Bitmap) data.getExtras().get("data");
        }

        if(bitmap == null)
            return;

        bitmap = getResizedBitmap(bitmap, 512);
        photoProfile.setImageBitmap(bitmap);
    }

    private Bitmap getResizedBitmap(Bitmap bitmap, int maxSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float bitmapRatio = (float) width / (float) height;

        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encoded;
    }

    public boolean cekKosong() {
        if(nama.getEditText().getText().toString().trim().isEmpty()
                || username.getEditText().getText().toString().trim().isEmpty()
                || alamat.getEditText().getText().toString().trim().isEmpty()
                || telp.getEditText().getText().toString().trim().isEmpty()
                || umur.getEditText().getText().toString().trim().isEmpty()) {
            Toast.makeText(EditProfile.this, "Data Masih Kosong!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void getUser(String usernameProfil) {
        class CheckUser extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {
                String usernameTemp = DatabaseAkun.getInstance(EditProfile.this)
                        .getDatabase()
                        .akunDao()
                        .getUser(usernameProfil);

                return usernameTemp;
            }

            @Override
            protected void onPostExecute(String userTemp) {
                super.onPostExecute(userTemp);
                if(userTemp != null) {
                    if(userTemp.equals(user.getUsername())) {
                        updateAkun();
                    } else {
                        Toast.makeText(EditProfile.this, "Username sudah ada!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if("admin".equals(username.getEditText().getText().toString().trim())) {
                        Toast.makeText(EditProfile.this, "Username Tidak Dizinkan!", Toast.LENGTH_SHORT).show();
                    } else {
                        updateAkun();
                    }
                }
            }
        }
        CheckUser checkUser = new CheckUser();
        checkUser.execute();
    }

    private void updateAkun() {
        BitmapDrawable drawable = (BitmapDrawable) photoProfile.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        final String name = nama.getEditText().getText().toString();
        final String usia = umur.getEditText().getText().toString();
        final String alamatUser = alamat.getEditText().getText().toString();
        final String noTelp = telp.getEditText().getText().toString();
        final String usernama = username.getEditText().getText().toString();
        final String image = bitmapToBase64(bitmap);

        class UpdateAkun extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseAkun.getInstance(EditProfile.this)
                        .getDatabase()
                        .akunDao()
                        .updateProfil(name, Integer.parseInt(usia), alamatUser, noTelp, usernama, image, user.getEmail());

                return null;
            }

            @Override
            protected  void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                userPreferences.setLogin(name, user.getJenisKelamin(), alamatUser, user.getEmail(), noTelp, usernama, user.getPassword(), Integer.parseInt(usia), user.getRoom());
                user = userPreferences.getUserLogin();
                Toast.makeText(EditProfile.this, "Update Profil Berhasil", Toast.LENGTH_SHORT).show();
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        }
        UpdateAkun updateAkun = new UpdateAkun();
        updateAkun.execute();
    }

    public void getAkun(String user, String password) {
        class GetAkun extends AsyncTask<Void, Void, Akun> {
            @Override
            protected Akun doInBackground(Void... voids) {
                Akun akun = DatabaseAkun.getInstance(EditProfile.this)
                        .getDatabase()
                        .akunDao()
                        .login(user, password);

                return akun;
            }

            @Override
            protected void onPostExecute(Akun akun) {
                super.onPostExecute(akun);
                if(akun == null) {
                    Toast.makeText(EditProfile.this, "Profil tidak ada!", Toast.LENGTH_SHORT).show();
                } else {
                    nama.getEditText().setText(akun.getNama());
                    umur.getEditText().setText(String.valueOf(akun.getUmur()));
                    alamat.getEditText().setText(akun.getAlamat());
                    telp.getEditText().setText(akun.getNoTelp());
                    username.getEditText().setText(akun.getUsername());
                    byte[] bytes = Base64.decode(akun.getImage(),Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    photoProfile.setImageBitmap(bitmap);
                }
            }
        }
        GetAkun getAkun = new GetAkun();
        getAkun.execute();
    }
}