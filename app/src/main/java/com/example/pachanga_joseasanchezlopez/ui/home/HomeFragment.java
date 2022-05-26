package com.example.pachanga_joseasanchezlopez.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pachanga_joseasanchezlopez.MainActivity;
import com.example.pachanga_joseasanchezlopez.R;
import com.example.pachanga_joseasanchezlopez.databinding.FragmentHomeBinding;
import com.example.pachanga_joseasanchezlopez.ui.adapter.EventoAdapter;
import com.example.pachanga_joseasanchezlopez.ui.events.NuevoEvento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private FirebaseAuth auth;
    private RecyclerView rvEventos;
    private ArrayList<NuevoEvento> eventos;
    private EventoAdapter adapter;
    private FirebaseFirestore mDatabase;


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuInflater inflater1 = getActivity().getMenuInflater();
        inflater1.inflate(R.menu.inicio, menu);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        auth = FirebaseAuth.getInstance();


        eventos = new ArrayList<>();
        rvEventos = root.findViewById(R.id.rvEventos);
        rvEventos.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarLista();

        adapter = new EventoAdapter(eventos);
        rvEventos.setAdapter(adapter);

        cargarDatos();
        return root;
    }
    ArrayList<NuevoEvento> recogidos=new ArrayList<>();

    private void llenarLista() {
        eventos.add(new NuevoEvento("25/09/2022", "Mi casa", "Avengers", "21:30", true));
        eventos.add(new NuevoEvento("01/01/2022", "Mi casa", "Pachangueros", "21:30", false));
        eventos.add(new NuevoEvento("Lunes", "Mi casa", "fdsf", "21:30",true));
        eventos.add(new NuevoEvento("Lunes", "Mi casa", "fdsf", "21:30",false));
        eventos.add(new NuevoEvento("Lunes", "Mi casa", "fdsf", "21:30", false));
        for (NuevoEvento n: recogidos) {
            eventos.add(n);
        }
        recogidos.clear();
    }

    private void cargarDatos() {
        mDatabase = FirebaseFirestore.getInstance();
        mDatabase.collection("pachangas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot t:task.getResult()) {
                    recogidos.add(new NuevoEvento(""+t.get("fecha"),""+t.get("lugar"), ""+t.get("nombre"), ""+t.get("hora"), t.getBoolean("privado")));
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}