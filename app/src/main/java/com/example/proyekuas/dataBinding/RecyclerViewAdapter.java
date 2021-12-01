package com.example.proyekuas.dataBinding;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyekuas.R;
import com.example.proyekuas.ReservationActivity;
import com.example.proyekuas.databinding.CardviewKamarBinding;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.viewHolder> {
    ArrayList<DataHotel> RoomList;

    public RecyclerViewAdapter(ArrayList<DataHotel> RoomList) {
        this.RoomList = RoomList;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        CardviewKamarBinding binding;

        public viewHolder(@NonNull CardviewKamarBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cardview_kamar,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.viewHolder holder, int position) {
        holder.binding.setRoom(RoomList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataHotel data = holder.binding.getRoom();
                Intent intent = new Intent(view.getContext(), ReservationActivity.class);
                intent.putExtra("urlGambar", data.getImgURL());
                intent.putExtra("typeRoom", data.getNamaKamar());
                intent.putExtra("harga", data.getStringHarga());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return RoomList.size();
    }
}
