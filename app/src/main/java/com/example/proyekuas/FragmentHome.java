package com.example.proyekuas;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.proyekuas.dataBinding.DataHotel;
import com.example.proyekuas.dataBinding.DataKamarHotel;
import com.example.proyekuas.dataBinding.RecyclerViewAdapter;
import com.example.proyekuas.SharedPreferences.Entity.User;
import com.example.proyekuas.SharedPreferences.Preferences.UserPreferences;
import com.example.proyekuas.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class FragmentHome extends Fragment {

    // TODO: Rename and change types of parameters
    private User user;
    private UserPreferences userPreferences;
    FragmentHomeBinding binding;
    ArrayList<DataHotel> RoomList;
    private RecyclerView recyclerView;
    TextView judul;


    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userPreferences = new UserPreferences(getContext());

        user = userPreferences.getUserLogin();

        judul = view.findViewById(R.id.username);

        judul.setText("Hi, "+user.getUsername()+"!");

        RoomList = new DataKamarHotel().Room;

        recyclerView = binding.rvKamar;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerViewAdapter(RoomList));

        checkLogin();
    }

    private void checkLogin() {
        /* this function will check if user login, akan memunculkan toast jika tidak redirect ke login activity */
        if(!userPreferences.checkLogin()) {
            startActivity(new Intent(getContext(), LoginActivity.class));
        }
    }
}