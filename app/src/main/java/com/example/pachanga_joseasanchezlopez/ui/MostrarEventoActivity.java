package com.example.pachanga_joseasanchezlopez.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.example.pachanga_joseasanchezlopez.R;
import com.example.pachanga_joseasanchezlopez.ui.adapter.EventoAdapter;

public class MostrarEventoActivity extends AppCompatActivity {

    private TextView tvNombreEvento, tvUbicacionEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_evento);

        tvNombreEvento=findViewById(R.id.tvNombreEventoVisualizar);
        tvUbicacionEvento=findViewById(R.id.tvUbicacionEvento);
        tvNombreEvento.setText(getIntent().getExtras().getString(EventoAdapter.NOMBRE_PACHANGA));
        tvUbicacionEvento.setText(getIntent().getExtras().getString(EventoAdapter.LUGAR_PACHANGA));

        tvUbicacionEvento.setOnClickListener(v -> {
            Uri intentUri = Uri.parse("geo:0,0?q="+Uri.encode(tvUbicacionEvento.getText().toString()));
            Intent intent = new Intent(Intent.ACTION_VIEW, intentUri);
            startActivity(intent);
        });
    }
}