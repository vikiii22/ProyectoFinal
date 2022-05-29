package com.example.pachanga_joseasanchezlopez.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pachanga_joseasanchezlopez.R;
import com.example.pachanga_joseasanchezlopez.ui.MostrarEventoActivity;
import com.example.pachanga_joseasanchezlopez.ui.events.NuevoEvento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Objects;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {
    public static final String NOMBRE_PACHANGA = "nombrePachanga";
    public static final String FECHA_PACHANGA = "fechaPachanga";
    public static final String LUGAR_PACHANGA = "lugarPachanga";
    public static final String HORA_PACHANGA = "horaPachanga";
    private FirebaseAuth auth;

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

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvNombrePachanga.setText(eventos.get(position).getNombre());
        holder.tvFechaPachanga.setText(eventos.get(position).getFecha());
        holder.tvHoraEvento.setText(eventos.get(position).getHora());

        if (!eventos.get(position).getPrivado().equals(true)) {
            holder.tvPrivado.setText("Evento público");
        } else {
            holder.tvPrivado.setText("Evento privado");
        }
        auth = FirebaseAuth.getInstance();
        String creador = Objects.requireNonNull(auth.getCurrentUser()).getEmail();
        if (eventos.get(position).getCreador().equals(creador)) {
            holder.cvEvento.setCardBackgroundColor(R.color.diferenciador);
        }

        holder.cvEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), eventos.get(position).getCreador(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), MostrarEventoActivity.class);
                intent.putExtra(NOMBRE_PACHANGA, eventos.get(position).getNombre());
                intent.putExtra(FECHA_PACHANGA, eventos.get(position).getFecha());
                intent.putExtra(LUGAR_PACHANGA, eventos.get(position).getLugar());
                intent.putExtra(HORA_PACHANGA, eventos.get(position).getHora());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clear() {

        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addAll(ArrayList<NuevoEvento> nuevoEventos) {
        eventos.addAll(nuevoEventos);
        notifyDataSetChanged();
    }

    public static class EventoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombrePachanga, tvFechaPachanga, tvHoraEvento, tvPrivado;
        CardView cvEvento;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombrePachanga = (TextView) itemView.findViewById(R.id.tvNombrePachanga);
            tvFechaPachanga = itemView.findViewById(R.id.tvFechaPachanga);
            tvHoraEvento = itemView.findViewById(R.id.tvHoraEvento);
            tvPrivado = itemView.findViewById(R.id.tvPrivado);
            cvEvento = itemView.findViewById(R.id.cvEvento);
        }
    }
}
