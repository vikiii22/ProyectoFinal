package com.example.pachanga_joseasanchezlopez.ui.gallery;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pachanga_joseasanchezlopez.R;
import com.example.pachanga_joseasanchezlopez.databinding.FragmentGalleryBinding;

import java.util.Calendar;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private TextView tvDatos, tvFecha;
    private EditText etNombreGrupo;
    private Button btCrear;
    private DatePickerDialog.OnDateSetListener setListener;


    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tvDatos=root.findViewById(R.id.tvDatos);
        tvFecha=root.findViewById(R.id.tvFecha);
        etNombreGrupo=root.findViewById(R.id.etNombreGrupo);
        btCrear=root.findViewById(R.id.btCrear);

        Calendar calendar=Calendar.getInstance();
        int anyo=calendar.get(Calendar.YEAR);
        int mes=calendar.get(Calendar.MONTH);
        int dia=calendar.get(Calendar.DAY_OF_MONTH);

        tvFecha.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), R.style.Theme_Pachanga_JoseASanchezLopez, setListener, anyo, mes, dia);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.CYAN));
            datePickerDialog.show();
        });

        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=mes+1;
                String date=day+"/"+month+"/"+year;
                tvFecha.setText(date);
            }
        };

        btCrear.setOnClickListener(v -> {
            tvDatos.setText(etNombreGrupo.getText() + " el día " + tvFecha.getText());
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