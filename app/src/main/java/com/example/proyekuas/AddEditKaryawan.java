package com.example.proyekuas;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyekuas.UnitTesting.ActivityUtil;
import com.example.proyekuas.UnitTesting.KaryawanPresenter;
import com.example.proyekuas.UnitTesting.KaryawanView;
import com.example.proyekuas.Volley.api.KaryawanApi;
import com.example.proyekuas.Volley.models.Karyawan;
import com.example.proyekuas.Volley.models.KaryawanResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AddEditKaryawan extends AppCompatActivity implements KaryawanView {

    private static final String[] JENIS_KELAMIN_LIST = new String[]{"Laki-laki",
            "Perempuan"};

    private static final String[] ROLE_LIST = new String[]{"Bell Boy", "Kitchen Staff",
            "Chef", "Receptionist", "Office Boy" };

    private EditText etNama, etNoKaryawan, etUmur;
    private AutoCompleteTextView edJenisKelamin, edRole;
    private RequestQueue queue;
    private LinearLayout layoutLoading;

    private KaryawanPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_karyawan);

        queue = Volley.newRequestQueue(this);
        etNama = findViewById(R.id.et_nama);
        etNoKaryawan = findViewById(R.id.et_noKaryawan);
        etUmur = findViewById(R.id.et_umur);
        edJenisKelamin = findViewById(R.id.ed_jenisKelamin);
        edRole = findViewById(R.id.ed_role);
        layoutLoading = findViewById(R.id.layout_loading);
        ArrayAdapter<String> adapterJenisKelamin =
                new ArrayAdapter<>(this, R.layout.item_list, JENIS_KELAMIN_LIST);
        edJenisKelamin.setAdapter(adapterJenisKelamin);

        ArrayAdapter<String> adapterRole =
                new ArrayAdapter<>(this, R.layout.item_list, ROLE_LIST);
        edRole.setAdapter(adapterRole);

        Button btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button btnSave = findViewById(R.id.btn_save);
        TextView tvTitle = findViewById(R.id.tv_title);
        long id = getIntent().getLongExtra("id", -1);

        if(id == -1) {
            tvTitle.setText("Tambah Karyawan");
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createKaryawan();
                }
            });
        } else {
            tvTitle.setText("Edit Karyawan");
            getKaryawanById(id);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateKaryawan(id);
                }
            });
        }
    }

    private void getKaryawanById(long id) {
        setLoading(true);

        StringRequest stringRequest = new StringRequest(GET,
                KaryawanApi.GET_BY_ID_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();

                KaryawanResponse karyawanResponse =
                        gson.fromJson(response, KaryawanResponse.class);

                Karyawan karyawan = karyawanResponse.getKaryawan();
                etNama.setText(karyawan.getNama_karyawan());
                etNoKaryawan.setText(karyawan.getNomor_karyawan());
                etUmur.setText(String.valueOf(karyawan.getUmur()));
                edJenisKelamin.setText(karyawan.getJenis_kelamin(), false);
                edRole.setText(karyawan.getRole(), false);

                Toast.makeText(AddEditKaryawan.this,
                        karyawanResponse.getMessage(), Toast.LENGTH_SHORT).show();
                setLoading(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                try{
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(AddEditKaryawan.this, errors.getString("message"),
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(AddEditKaryawan.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");

                return headers;
            }
        };
        queue.add(stringRequest);
    }

    private void createKaryawan() {
        setLoading(true);

        Integer umur = null;
        if(!etUmur.getText().toString().isEmpty()) {
            umur = Integer.valueOf(etUmur.getText().toString());
        }

        Karyawan karyawan = new Karyawan(
                etNama.getText().toString(),
                etNoKaryawan.getText().toString(),
                umur,
                edJenisKelamin.getText().toString(),
                edRole.getText().toString()
        );

        StringRequest stringRequest = new StringRequest(POST, KaryawanApi.ADD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        KaryawanResponse karyawanResponse =
                                gson.fromJson(response, KaryawanResponse.class);

                        Toast.makeText(AddEditKaryawan.this,
                                karyawanResponse.getMessage(), Toast.LENGTH_SHORT).show();

//                        presenter.onKaryawanClicked();
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                        setLoading(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);

                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(AddEditKaryawan.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(AddEditKaryawan.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
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
            public String getBodyContentType() {
                return "application/json";
            }
        };
        queue.add(stringRequest);
    }

    //Code Unit Testing
    @Override
    public String getNamaKaryawan() { return etNama.getText().toString(); }
    @Override
    public void showNamaError(String message) { etNama.setError(message); }
    @Override
    public String getNomorKaryawan() { return etNoKaryawan.getText().toString(); }
    @Override
    public void showNomorKaryawanError(String message) { etNoKaryawan.setError(message); }
    @Override
    public String getUmurKaryawan() { return etUmur.getText().toString(); }
    @Override
    public void showUmurKaryawanError(String message) { etUmur.setError(message); }
    @Override
    public String getJenisKelamin() { return edJenisKelamin.getText().toString(); }
    @Override
    public void showJenisKelaminError(String message) { edJenisKelamin.setError(message); }
    @Override
    public String getRoleKaryawan() { return edRole.getText().toString(); }
    @Override
    public void showRoleKaryawanError(String message) { edRole.setError(message); }
    @Override
    public void startAddEditKaryawan() { new ActivityUtil(this).startMainAddEditKaryawan(); }
    @Override
    public void showAddEditKaryawanError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void showErrorResponse(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void updateKaryawan(long id){
        setLoading(true);

        Karyawan karyawan = new Karyawan(
                etNama.getText().toString(),
                etNoKaryawan.getText().toString(),
                Integer.valueOf(etUmur.getText().toString()),
                edJenisKelamin.getText().toString(),
                edRole.getText().toString()
        );

        StringRequest stringRequest = new StringRequest(PUT,
                KaryawanApi.UPDATE_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();

                KaryawanResponse karyawanResponse =
                        gson.fromJson(response, KaryawanResponse.class);

                Toast.makeText(AddEditKaryawan.this,
                        karyawanResponse.getMessage(), Toast.LENGTH_SHORT).show();

                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                setLoading(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);

                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(AddEditKaryawan.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(AddEditKaryawan.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
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
            public String getBodyContentType() {

                return "application/json";
            }
        };
        queue.add(stringRequest);
    }

    private void setLoading(boolean isLoading) {
        if (isLoading) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.VISIBLE);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.INVISIBLE);
        }
    }
}