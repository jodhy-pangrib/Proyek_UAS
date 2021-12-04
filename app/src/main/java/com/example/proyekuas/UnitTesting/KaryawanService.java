package com.example.proyekuas.UnitTesting;

import static com.android.volley.Request.Method.POST;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.proyekuas.AddEditKaryawan;
import com.example.proyekuas.Volley.api.KaryawanApi;
import com.example.proyekuas.Volley.models.Karyawan;
import com.example.proyekuas.Volley.models.KaryawanResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class KaryawanService {
    private RequestQueue queue;

    public void karyawan(final KaryawanView view, Karyawan karyawan, final
    KaryawanCallback callback)
    {
        StringRequest stringRequest = new StringRequest(POST, KaryawanApi.ADD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        KaryawanResponse karyawanResponse =
                                gson.fromJson(response, KaryawanResponse.class);

                        if(karyawanResponse.getMessage().equalsIgnoreCase("Add Karyawan Success")){
                            callback.onSuccess(true, karyawan);
                        } else {
                            callback.onError();
                            view.showAddEditKaryawanError(karyawanResponse.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    view.showErrorResponse(errors.getString("message"));
                    callback.onError();

                } catch (Exception e) {
                    view.showErrorResponse(e.getMessage());
                    callback.onError();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");

                return headers;
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                 /* Serialisasi data dari java object MahasiswaResponse
                 menjadi JSON string menggunakan Gson */
                String requestBody = gson.toJson(karyawan);

                return requestBody.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() { return "application/json"; }
        };
        queue.add(stringRequest);
    }

    public Boolean getValid(final KaryawanView view, Karyawan karyawan) {
        final Boolean[] bool = new Boolean[1];
        karyawan(view, karyawan, new KaryawanCallback() {
            @Override
            public void onSuccess(boolean value, Karyawan karyawan) {
                bool[0] = true;
            }

            @Override
            public void onError() {
                bool[0] = false;
            }
        });
        return bool[0];
    }
}
