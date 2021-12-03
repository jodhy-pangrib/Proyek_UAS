package com.example.proyekuas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.proyekuas.Room.Database.DatabaseAkun;
import com.example.proyekuas.Room.Entity.Akun;
import com.example.proyekuas.SharedPreferences.Entity.User;
import com.example.proyekuas.SharedPreferences.Preferences.UserPreferences;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentProfile extends Fragment {
    TextView judul, nama, jenisKelamin, umur, alamat, email, noTelp, usernameProfil, jenisKamar;
    ImageView arrow;
    MaterialButton logout, edit;
    CircleImageView img;
    private User user;
    private UserPreferences userPreferences;
    private FirebaseAuth mAuth;

    public FragmentProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        userPreferences = new UserPreferences(getContext());
        logout = view.findViewById(R.id.btnLogout);

        arrow = view.findViewById(R.id.arrow);
        judul = view.findViewById(R.id.judul);
        nama = view.findViewById(R.id.isiNamaProfil);
        jenisKelamin = view.findViewById(R.id.isiJenisKelaminProfil);
        umur = view.findViewById(R.id.isiUmurProfil);
        alamat = view.findViewById(R.id.isiAlamatProfil);
        email = view.findViewById(R.id.isiEmailProfil);
        noTelp = view.findViewById(R.id.isiTeleponProfil);
        usernameProfil = view.findViewById(R.id.isiUsernameProfil);
        jenisKamar = view.findViewById(R.id.isiJenisKamarProfil);
        img = view.findViewById(R.id.img);
        edit = view.findViewById(R.id.btnEdit);

        judul.setText("Akun");
        arrow.setVisibility(view.GONE);

        user = userPreferences.getUserLogin();

        getAkun(user.getUsername(),user.getPassword());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPreferences.logout();
                mAuth.signOut();
                checkLogin();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),EditProfile.class);
                startActivityForResult(intent,1);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == getActivity().RESULT_OK) {
                user = userPreferences.getUserLogin();
                getAkun(user.getUsername(),user.getPassword());
            }
        }
    }

    private void checkLogin() {
        /* this function will check if user login, akan memunculkan toast jika tidak redirect ke login activity */
        if(!userPreferences.checkLogin()) {
            Intent intent = new Intent(getContext(),LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    public void getAkun(String username, String password) {
        class GetAkun extends AsyncTask<Void, Void, Akun> {
            @Override
            protected Akun doInBackground(Void... voids) {
                Akun akun = DatabaseAkun.getInstance(getContext())
                        .getDatabase()
                        .akunDao()
                        .login(username, password);

                return akun;
            }

            @Override
            protected void onPostExecute(Akun akun) {
                super.onPostExecute(akun);
                if(akun == null) {
                    Toast.makeText(getContext(), "Profil tidak ada!", Toast.LENGTH_SHORT).show();
                } else {
                    nama.setText(akun.getNama());
                    jenisKelamin.setText(akun.getJenisKelamin());
                    umur.setText(String.valueOf(akun.getUmur()));
                    alamat.setText(akun.getAlamat());
                    email.setText(akun.getEmail());
                    noTelp.setText(akun.getNoTelp());
                    usernameProfil.setText(akun.getUsername());
                    jenisKamar.setText(akun.getTypeRoom());
                    byte[] bytes = Base64.decode(akun.getImage(),Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    img.setImageBitmap(bitmap);
                }
            }
        }
        GetAkun getAkun = new GetAkun();
        getAkun.execute();
    }
}
