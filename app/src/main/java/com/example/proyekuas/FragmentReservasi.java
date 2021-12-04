package com.example.proyekuas;

import static com.android.volley.Request.Method.DELETE;
import static com.android.volley.Request.Method.GET;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyekuas.Volley.Method;
import com.example.proyekuas.Volley.adapters.ReservasiAdapter;
import com.example.proyekuas.Volley.api.ReservasiApi;
import com.example.proyekuas.Volley.models.ReservasiResponse;
import com.example.proyekuas.Volley.models.ReservasiResponseAll;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentReservasi extends Fragment {

    private SwipeRefreshLayout srRerservasi;
    private ReservasiAdapter adapter;
    private SearchView svReservasi;
    private LinearLayout layoutLoading;
    private RequestQueue queue;

    public FragmentReservasi() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Pendeklarasian request queue
        queue = Volley.newRequestQueue(getContext());

        layoutLoading = view.findViewById(R.id.layout_loading);
        srRerservasi = view.findViewById(R.id.sr_reservasi);
        svReservasi = view.findViewById(R.id.sv_reservasi);

        srRerservasi.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllReservasi();
            }
        });

        svReservasi.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        FloatingActionButton fabAdd = view.findViewById(R.id.fab_addReservasi);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), AddEditReservasi.class);
                startActivityForResult(i, 123);
            }
        });

        RecyclerView rvReservasi = view.findViewById(R.id.rv_reservasi);
        adapter = new ReservasiAdapter(new ArrayList<>(), getActivity(), new Method() {
            @Override
            public void delete(long id) {
                deleteReservasi(id);
            }
            @Override
            public void getId(long id) {
                Intent i = new Intent(getContext(), AddEditReservasi.class);
                i.putExtra("id", id);
                startActivityForResult(i,123);
            }
        });
        rvReservasi.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvReservasi.setAdapter(adapter);

        getAllReservasi();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123 && resultCode == Activity.RESULT_OK)
            getAllReservasi();
    }

    private void getAllReservasi() {
        srRerservasi.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(GET, ReservasiApi.GET_ALL_URL, new Response.Listener<String>() {
            @Override public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API menjadi java object MahasiswaResponse menggunakan Gson */
                ReservasiResponseAll reservasiResponse = gson.fromJson(response, ReservasiResponseAll.class);

                adapter.setReservasiList(reservasiResponse.getReservasiList());
                adapter.getFilter().filter(svReservasi.getQuery());

                Toast.makeText(getContext(), reservasiResponse.getMessage(), Toast.LENGTH_LONG).show();

                srRerservasi.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                srRerservasi.setRefreshing(false);

                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(getContext(), errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            // Menambahkan header pada request
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");

                return headers;
            }
        };

        // Menambahkan request ke request queue
        queue.add(stringRequest);
    }

    public void deleteReservasi(long id) {
        setLoading(true);

        StringRequest stringRequest = new StringRequest(DELETE, ReservasiApi.DELETE_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API menjadi java object MahasiswaResponse menggunakan Gson */
                ReservasiResponse reservasiResponse = gson.fromJson(response, ReservasiResponse.class);

                setLoading(false);
                Toast.makeText(getContext(), reservasiResponse.getMessage(), Toast.LENGTH_SHORT).show();

                getAllReservasi();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);

                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(getContext(), errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            // Menambahkan header pada request
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");

                return headers;
            }
        };

        // Menambahkan request ke request queue
        queue.add(stringRequest);
    }

    // Fungsi untuk menampilkan layout loading
    private void setLoading(boolean isLoading) {
        if (isLoading) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE); layoutLoading.setVisibility(View.VISIBLE);
        } else {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservasi, container, false);
    }
}