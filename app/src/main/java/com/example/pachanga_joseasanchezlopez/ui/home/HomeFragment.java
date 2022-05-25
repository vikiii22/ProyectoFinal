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
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private FirebaseAuth auth;
    private TextView tvCorreo, tvNombreUsuario;
    private RecyclerView rvEventos;
    private ArrayList<NuevoEvento> eventos;

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
        tvCorreo=root.findViewById(R.id.tvCorreoUsuario);
        tvNombreUsuario=root.findViewById(R.id.tvNombreUsuario);
        auth=FirebaseAuth.getInstance();

        tvCorreo.setText(auth.getCurrentUser().getEmail());
        tvNombreUsuario.setText(auth.getCurrentUser().getDisplayName());

        eventos=new ArrayList<>();
        rvEventos=root.findViewById(R.id.rvEventos);
        rvEventos.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarLista();

        EventoAdapter adapter = new EventoAdapter(eventos);
        rvEventos.setAdapter(adapter);
        /*final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/
        return root;
    }

    private void llenarLista() {
        eventos.add(new NuevoEvento("Lunes","Mi casa","fdsf"));
        eventos.add(new NuevoEvento("Lunes","Mi casa","fdsf"));
        eventos.add(new NuevoEvento("Lunes","Mi casa","fdsf"));
        eventos.add(new NuevoEvento("Lunes","Mi casa","fdsf"));
        eventos.add(new NuevoEvento("Lunes","Mi casa","fdsf"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}