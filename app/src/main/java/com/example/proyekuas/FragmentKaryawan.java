package com.example.proyekuas;

import static com.android.volley.Request.Method.DELETE;
import static com.android.volley.Request.Method.GET;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.proyekuas.Volley.adapters.KaryawanAdapter;
import com.example.proyekuas.Volley.api.KaryawanApi;
import com.example.proyekuas.Volley.models.KaryawanResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentKaryawan extends Fragment  {

    public static final int LAUNCH_ADD_ACTIVITY = 123;

    private SwipeRefreshLayout srKaryawan;
    private KaryawanAdapter adapter;
    private SearchView svKaryawan;
    private LinearLayout layoutLoading;
    private RequestQueue queue;

    public FragmentKaryawan() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Pendeklarasian request queue
        queue = Volley.newRequestQueue(getContext());

        layoutLoading = view.findViewById(R.id.layout_loading);
        srKaryawan = view.findViewById(R.id.sr_karyawan);
        svKaryawan = view.findViewById(R.id.sv_karyawan);

        srKaryawan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllKaryawan();
            }
        });

        svKaryawan.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        FloatingActionButton fabAdd = view.findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        RecyclerView rvKaryawan = view.findViewById(R.id.rv_karyawan);
        adapter = new KaryawanAdapter(new ArrayList<>(), getActivity(), new Method() {
            @Override
            public void delete(long id) {
                deleteKaryawan(id);
            }
        });
        rvKaryawan.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvKaryawan.setAdapter(adapter);

        getAllKaryawan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_ADD_ACTIVITY && resultCode == Activity.RESULT_OK)
            getAllKaryawan();
    }

    private void getAllKaryawan() {
        srKaryawan.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(GET, KaryawanApi.GET_ALL_URL, new Response.Listener<String>() {
            @Override public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API menjadi java object MahasiswaResponse menggunakan Gson */
                KaryawanResponse karyawanResponse = gson.fromJson(response, KaryawanResponse.class);

                adapter.setKaryawanList(karyawanResponse.getKaryawanList());
                adapter.getFilter().filter(svKaryawan.getQuery());

                Toast.makeText(getContext(), karyawanResponse.getMessage(), Toast.LENGTH_LONG).show();

                srKaryawan.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                srKaryawan.setRefreshing(false);

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

    public void deleteKaryawan(long id) {
        setLoading(true);

        StringRequest stringRequest = new StringRequest(DELETE, KaryawanApi.DELETE_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API menjadi java object MahasiswaResponse menggunakan Gson */
                KaryawanResponse karyawanResponse = gson.fromJson(response, KaryawanResponse.class);

                setLoading(false);
                Toast.makeText(getContext(), karyawanResponse.getMessage(), Toast.LENGTH_SHORT).show();

                getAllKaryawan();
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
        return inflater.inflate(R.layout.fragment_karyawan, container, false);
    }
}