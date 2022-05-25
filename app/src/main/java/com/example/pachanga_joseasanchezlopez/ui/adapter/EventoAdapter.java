package com.example.pachanga_joseasanchezlopez.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pachanga_joseasanchezlopez.R;
import com.example.pachanga_joseasanchezlopez.ui.events.NuevoEvento;

import java.util.ArrayList;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {

    ArrayList<NuevoEvento> eventos;

    public EventoAdapter(ArrayList<NuevoEvento> eventos) {
        this.eventos = eventos;
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento, null, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        holder.tvNombrePachanga.setText(eventos.get(position).getNombre());
        holder.tvFechaPachanga.setText(eventos.get(position).getFecha());
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public static class EventoViewHolder extends RecyclerView.ViewHolder{
        TextView tvNombrePachanga, tvFechaPachanga;
        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombrePachanga=(TextView) itemView.findViewById(R.id.tvNombrePachanga);
            tvFechaPachanga=itemView.findViewById(R.id.tvFechaPachanga);
        }
    }
}
