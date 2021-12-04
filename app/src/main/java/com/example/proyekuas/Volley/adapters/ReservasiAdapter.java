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

import com.example.proyekuas.R;
import com.example.proyekuas.Volley.Method;
import com.example.proyekuas.Volley.models.Reservasi;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class ReservasiAdapter extends RecyclerView.Adapter<ReservasiAdapter.ViewHolder> implements Filterable {
    private List<Reservasi> reservasiList, filteredReservasiList;
    private Context context;

    public ReservasiAdapter(List<Reservasi> reservasiList, Context context) {
        this.reservasiList = reservasiList;
        filteredReservasiList = new ArrayList<>(reservasiList);
        this.context = context;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSequenceString = charSequence.toString();
                List<Reservasi> filtered = new ArrayList<>();

                if (charSequenceString.isEmpty()) {
                    filtered.addAll(reservasiList);
                } else {
                    for (Reservasi reservasi : reservasiList) {
                        if (reservasi.getNama().toLowerCase() .contains(charSequenceString.toLowerCase()))
                            filtered.add(reservasi);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered; return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredReservasiList.clear();
                filteredReservasiList.addAll((List<Reservasi>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_reservasi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reservasi reservasi = filteredReservasiList.get(position);
        if(reservasi != null) {
            holder.tvNama.setText(reservasi.getNama());
            holder.tvRoomType.setText(reservasi.getRoom_type());
            holder.tvTotal.setText("Rp. "+String.valueOf(reservasi.getTotal_harga()));
            holder.tvCheckInOut.setText(reservasi.getCheckIn() + " - " + reservasi.getCheckOut());
        }
    }

    @Override
    public int getItemCount() {
        return filteredReservasiList.size();
    }

    public void setReservasiList(List<Reservasi> reservasiList) {
        this.reservasiList = reservasiList;
        filteredReservasiList = new ArrayList<>(reservasiList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvCheckInOut, tvRoomType, tvTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tv_nama);
            tvCheckInOut = itemView.findViewById(R.id.tv_checkInOut);
            tvRoomType = itemView.findViewById(R.id.tv_roomType);
            tvTotal = itemView.findViewById(R.id.tv_totalHarga);
        }
    }
}
