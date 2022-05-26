package com.example.pachanga_joseasanchezlopez.ui.events;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pachanga_joseasanchezlopez.R;
import com.example.pachanga_joseasanchezlopez.databinding.FragmentGalleryBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class EventsFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private TextView tvHora, tvFecha;
    private EditText etNombreGrupo, etUbicacion;
    private Button btCrear;


    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EventsViewModel eventsViewModel =
                new ViewModelProvider(this).get(EventsViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tvHora = root.findViewById(R.id.tvHora);
        tvFecha = root.findViewById(R.id.tvFecha);
        etNombreGrupo = root.findViewById(R.id.etNombreGrupo);
        etUbicacion = root.findViewById(R.id.etUbicacion);
        btCrear = root.findViewById(R.id.btCrear);

        Calendar calendar = Calendar.getInstance();
        int anyo = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        tvFecha.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = mes + 1;
                    String date = day + "/" + month + "/" + year;
                    tvFecha.setText(date);
                }
            }, anyo, mes, dia);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
            datePickerDialog.show();
        });

        tvHora.setOnClickListener(v -> {
            Calendar c=Calendar.getInstance();
            int hora=c.get(Calendar.HOUR_OF_DAY);
            int minuto=c.get(Calendar.MINUTE);
            TimePickerDialog timePicker;
            timePicker=new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    tvHora.setText(i + ":" + i1);
                }
            }, hora, minuto, true);
            timePicker.setTitle("Selecciona la hora");
            timePicker.show();
        });

        btCrear.setOnClickListener(v -> {
            if (etNombreGrupo.getText().length() == 0 || tvFecha.getText().toString().equals("Seleccionar Fecha") || etUbicacion.getText().length() == 0) {
                return;
            } else {
                NuevoEvento nuevo = new NuevoEvento(tvFecha.getText().toString(), etUbicacion.getText().toString(), etNombreGrupo.getText().toString(), tvHora.getText().toString());
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("pachangas").document().set(nuevo);
            }
        });

        //final TextView textView = binding.textGallery;
        //galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void showDatePickerDialog() {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}