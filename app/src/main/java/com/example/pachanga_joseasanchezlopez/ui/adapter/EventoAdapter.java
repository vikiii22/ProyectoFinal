package com.example.pachanga_joseasanchezlopez.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento, null, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvNombrePachanga.setText(eventos.get(position).getNombre());
        holder.tvFechaPachanga.setText(eventos.get(position).getFecha());
        holder.tvHoraEvento.setText(eventos.get(position).getHora());

        holder.cvEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), eventos.get(position).getNombre(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public static class EventoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombrePachanga, tvFechaPachanga, tvHoraEvento;
        CardView cvEvento;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombrePachanga = (TextView) itemView.findViewById(R.id.tvNombrePachanga);
            tvFechaPachanga = itemView.findViewById(R.id.tvFechaPachanga);
            tvHoraEvento=itemView.findViewById(R.id.tvHoraEvento);
            cvEvento=itemView.findViewById(R.id.cvEvento);
        }
    }
}
