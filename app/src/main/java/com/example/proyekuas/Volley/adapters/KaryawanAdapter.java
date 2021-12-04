package com.example.proyekuas.Volley.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyekuas.Volley.Method;
import com.example.proyekuas.R;
import com.example.proyekuas.Volley.models.Karyawan;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class KaryawanAdapter extends RecyclerView.Adapter<KaryawanAdapter.ViewHolder> implements Filterable {
    private List<Karyawan> karyawanList, filteredKaryawanList;
    private Context context;
    private Method callback;

    public KaryawanAdapter(List<Karyawan> karyawanList, Context context, Method callback) {
        this.karyawanList = karyawanList;
        filteredKaryawanList = new ArrayList<>(karyawanList);
        this.context = context;
        this.callback = callback;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSequenceString = charSequence.toString();
                List<Karyawan> filtered = new ArrayList<>();

                if (charSequenceString.isEmpty()) {
                    filtered.addAll(karyawanList);
                } else {
                    for (Karyawan karyawan : karyawanList) {
                        if (karyawan.getNama_karyawan().toLowerCase() .contains(charSequenceString.toLowerCase()))
                            filtered.add(karyawan);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered; return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredKaryawanList.clear();
                filteredKaryawanList.addAll((List<Karyawan>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_karyawan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Karyawan karyawan = filteredKaryawanList.get(position);
        if(karyawan != null) {
            holder.tvNama.setText(karyawan.getNama_karyawan());
            holder.tvNomorKaryawan.setText(karyawan.getNomor_karyawan());
            holder.tvRole.setText(karyawan.getRole());
            holder.tvInfo.setText(karyawan.getUmur() + " tahun - " + karyawan.getJenis_kelamin());

            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context);
                    materialAlertDialogBuilder
                            .setTitle("Konfirmasi")
                            .setMessage("Apakah anda yakin ingin menghapus data karyawan ini?")
                            .setNegativeButton("Batal", null)
                            .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    callback.delete(karyawan.getId());
                                }
                            })
                            .show();
                }
            });

            holder.cvKaryawan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.getId(karyawan.getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return filteredKaryawanList.size();
    }

    public void setKaryawanList(List<Karyawan> karyawanList) {
        this.karyawanList = karyawanList;
        filteredKaryawanList = new ArrayList<>(karyawanList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvInfo, tvNomorKaryawan, tvRole;
        ImageButton btnDelete;
        CardView cvKaryawan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tv_namaKaryawan);
            tvInfo = itemView.findViewById(R.id.tv_info);
            tvNomorKaryawan = itemView.findViewById(R.id.tv_nomorKaryawan);
            tvRole = itemView.findViewById(R.id.tv_role);
            btnDelete = itemView.findViewById(R.id.btn_deleteKaryawan);
            cvKaryawan = itemView.findViewById(R.id.cv_karyawan);
        }
    }
}
