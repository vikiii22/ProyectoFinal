package com.example.pachanga_joseasanchezlopez.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pachanga_joseasanchezlopez.R;
import com.example.pachanga_joseasanchezlopez.ui.adapter.EventoAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MostrarEventoActivity extends AppCompatActivity {

    private TextView tvNombreEvento, tvUbicacionEvento;
    private Button btSalir, btAgregar;
    private ListView lvJugadores;
    private ArrayList<String> jugadores;
    private ArrayAdapter<String> adapter;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_evento);

        auth=FirebaseAuth.getInstance();
        tvNombreEvento=findViewById(R.id.tvNombreEventoVisualizar);
        tvUbicacionEvento=findViewById(R.id.tvUbicacionEvento);
        btSalir=findViewById(R.id.btSalir);
        btAgregar=findViewById(R.id.btAgregar);
        lvJugadores=findViewById(R.id.lvJugadores);
        jugadores=new ArrayList<>();
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, jugadores);
        lvJugadores.setAdapter(adapter);

        tvNombreEvento.setText(getIntent().getExtras().getString(EventoAdapter.NOMBRE_PACHANGA));
        tvUbicacionEvento.setText(getIntent().getExtras().getString(EventoAdapter.LUGAR_PACHANGA));

        tvUbicacionEvento.setOnClickListener(v -> {
            Uri intentUri = Uri.parse("geo:0,0?q="+Uri.encode(tvUbicacionEvento.getText().toString()));
            Intent intent = new Intent(Intent.ACTION_VIEW, intentUri);
            startActivity(intent);
        });

        btSalir.setOnClickListener(v -> {
            finish();
        });

        btAgregar.setOnClickListener(v -> {
            if (jugadores.size()<=10){
                /*if (!jugadores.contains(auth.getCurrentUser().getEmail()))*/
                jugadores.add(auth.getCurrentUser().getEmail());
                adapter.notifyDataSetChanged();
            }else{
                return;
            }
        });
    }
}