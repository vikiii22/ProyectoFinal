package com.example.pachanga_joseasanchezlopez.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pachanga_joseasanchezlopez.R;
import com.example.pachanga_joseasanchezlopez.databinding.FragmentHomeBinding;
import com.example.pachanga_joseasanchezlopez.ui.adapter.EventoAdapter;
import com.example.pachanga_joseasanchezlopez.ui.events.NuevoEvento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private FirebaseAuth auth;
    private RecyclerView rvEventos;
    private ArrayList<NuevoEvento> eventos;
    private EventoAdapter adapter;
    private FirebaseFirestore mDatabase;
    private SwipeRefreshLayout refreshLayout;


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
        refreshLayout = root.findViewById(R.id.swipeRefresh);
        rvEventos.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarLista();

        adapter = new EventoAdapter(eventos);
        rvEventos.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                eventos.clear();
                recogidos.clear();
                llenarLista();
                adapter = new EventoAdapter(eventos);
                rvEventos.setAdapter(adapter);
                cargarDatos();
                new CargaDesdeBD().execute();
            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("¿Desea eliminar el evento?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        if (eventos.get(viewHolder.getAdapterPosition()).getCreador().equals(auth.getCurrentUser().getEmail())) {
                            String evento = eventos.get(viewHolder.getAdapterPosition()).getId();
                            System.out.println(eventos.get(viewHolder.getAdapterPosition()).getNombre());
                            db.collection("pachangas").document(evento).delete();
                            eventos.remove(viewHolder.getAdapterPosition());
                            adapter.notifyDataSetChanged();
                        } else {
                            return;
                        }
                    }
                });
                builder.setNegativeButton("No", null);
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvEventos);

        return root;
    }

    ArrayList<NuevoEvento> recogidos = new ArrayList<>();

    private void llenarLista() {
        eventos.add(new NuevoEvento("1", "25/09/2022", "Mi casa", "Avengers", "21:30", true, "marina@gmail.com"));
        eventos.addAll(recogidos);
        recogidos.clear();
    }

    private void cargarDatos() {
        mDatabase = FirebaseFirestore.getInstance();
        mDatabase.collection("pachangas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot t : task.getResult()) {
                    recogidos.add(new NuevoEvento("" + t.get("id"), "" + t.get("fecha"), "" + t.get("lugar"), "" + t.get("nombre"), "" + t.get("hora"), t.getBoolean("privado"), "" + t.get("creador")));
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class CargaDesdeBD extends AsyncTask<Void, Void, ArrayList<NuevoEvento>> {
        @Override
        protected ArrayList<NuevoEvento> doInBackground(Void... voids) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return eventos;
        }

        @Override
        protected void onPostExecute(ArrayList<NuevoEvento> nuevoEventos) {
            super.onPostExecute(recogidos);

            adapter.addAll(recogidos);

            adapter.clear();

            refreshLayout.setRefreshing(false);
        }

    }
}