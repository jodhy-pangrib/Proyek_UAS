package com.example.proyekuas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyekuas.SharedPreferences.Entity.User;
import com.example.proyekuas.SharedPreferences.Preferences.UserPreferences;
import com.google.android.material.button.MaterialButton;

public class FragmentProfile extends Fragment {
    TextView judul, nama, jenisKelamin, umur, alamat, email, noTelp, username, jenisKamar;
    ImageView arrow;
    MaterialButton logout;
    private User user;
    private UserPreferences userPreferences;

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
        username = view.findViewById(R.id.isiUsernameProfil);
        jenisKamar = view.findViewById(R.id.isiJenisKamarProfil);

        judul.setText("Akun");
        arrow.setVisibility(view.GONE);

        user = userPreferences.getUserLogin();

        nama.setText(user.getNama());
        jenisKelamin.setText(user.getJenisKelamin());
        umur.setText(String.valueOf(user.getUmur()));
        alamat.setText(user.getAlamat());
        email.setText(user.getEmail());
        noTelp.setText(user.getNoTelp());
        username.setText(user.getUsername());
        jenisKamar.setText(user.getRoom());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPreferences.logout();
                checkLogin();
            }
        });
    }
    private void checkLogin() {
        /* this function will check if user login, akan memunculkan toast jika tidak redirect ke login activity */
        if(!userPreferences.checkLogin()) {
            Intent intent = new Intent(getContext(),LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
}
